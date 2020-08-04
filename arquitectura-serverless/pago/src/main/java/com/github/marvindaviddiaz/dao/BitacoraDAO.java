package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.PeticionPagoDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitacoraDAO {

    private static final Logger logger = Logger.getLogger(BitacoraDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));

    private static final String INFO_SERVICIO = "select t.nombre, s.nombre  from servicio s right join tercero t on t.id = s.tercero where s.id = :servicio";

    private static final String INSERT_BITACORA = "insert into bitacora(id, fecha, usuario, estado, tipo, tercero, servicio, cuenta, monto, respuesta_tercero, mensaje_error) " +
            "values(:id, :fecha, :usuario, :estado, :tipo, :tercero, :servicio, :cuenta, :monto, :respuestaTercero, :error) ";

    private static final String INSERT_BITACORA_IDENTIFICADOR = "insert into bitacora_identificador(bitacora, identificador, valor) " +
            "values(:bitacora, :identificador, :valor) ";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public void guardarBitacora(String uuid, Integer usuario, PeticionPagoDTO peticion, String respuestaTercero, String error) {

        AtomicReference<String> nombreServicio = new AtomicReference<>(null);
        AtomicReference<String> nombreTercero =  new AtomicReference<>(null);
        jdbcTemplate.query(INFO_SERVICIO, new MapSqlParameterSource()
                        .addValue("servicio", peticion.getServicio()),
                (rs, rowNum) -> {
                    nombreTercero.set(rs.getString(1));
                    nombreServicio.set(rs.getString(2));
                    return null;
                });

        // TODO: run all on a transaction
        jdbcTemplate.update(INSERT_BITACORA, new MapSqlParameterSource()
                .addValue("id", uuid)
                .addValue("fecha", new Date())
                .addValue("usuario", usuario)
                .addValue("estado", "P")
                .addValue("tipo", "P")
                .addValue("tercero", nombreTercero.get())
                .addValue("servicio", nombreServicio.get())
                .addValue("cuenta", peticion.getCuenta())
                .addValue("monto", peticion.getIdentificadores().getOrDefault("VALOR", null))
                .addValue("respuestaTercero", respuestaTercero == null ? null : respuestaTercero.getBytes())
                .addValue("error", error));

        peticion.getIdentificadores().forEach((identificador, valor) ->
                jdbcTemplate.update(INSERT_BITACORA_IDENTIFICADOR, new MapSqlParameterSource()
                        .addValue("bitacora", uuid)
                        .addValue("identificador", identificador)
                        .addValue("valor", valor))
        );

    }

}
