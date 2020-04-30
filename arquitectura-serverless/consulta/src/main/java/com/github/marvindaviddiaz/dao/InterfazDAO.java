package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.InterfazDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfazDAO {

    private static final Logger logger = Logger.getLogger(InterfazDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));

    private static final String QUERY = "select obj.url, obj.protocolo, obj.metodo, obj.mensaje " +
            "from interfaz obj " +
            "where obj.servicio = :servicio and tipo_operacion = :tipoOperacion";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public InterfazDTO obtenerInterfaz(Integer servicio, String tipoOperacion) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("servicio", servicio)
                .addValue("tipoOperacion", tipoOperacion);
        return jdbcTemplate.queryForObject(QUERY, namedParameters,
                (rs, rowNum) -> {
                    InterfazDTO p = new InterfazDTO();
                    p.setUrl(rs.getString(1));
                    p.setProtocolo(rs.getString(2));
                    p.setMetodo(rs.getString(3));
                    p.setMensaje(rs.getString(4));
                    return p;
                });
    }

}
