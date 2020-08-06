package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.BitacoraDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private static final String BITACORA = "select id, fecha, estado, tipo, tercero, servicio, cuenta, monto, mensaje_error " +
            " from bitacora where usuario = :usuario and fecha between :inicio and :fin";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public List<BitacoraDTO> consulta(Integer usuario, Date fechaInicio, Date fechaFin, String filtro, Integer pagina) {
        List<BitacoraDTO> list = new ArrayList<>();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("inicio", fechaInicio)
                .addValue("fin", fechaFin);
        String query = BITACORA;
        if (filtro != null) {
            query += " and (tercero = :filtro or servicio = :filtro)";
            namedParameters = namedParameters.addValue("filtro", filtro);
        }
        query += " order by fecha desc + " + (pagina * 10) + ", 10";
        jdbcTemplate.query(query, namedParameters,
                (rs, rowNum) -> {
                    BitacoraDTO dto = new BitacoraDTO();
                    dto.setId(rs.getString(1));
                    dto.setFecha(rs.getDate(2));
                    dto.setEstado(rs.getString(3));
                    dto.setTipo(rs.getString(4));
                    dto.setTercero(rs.getString(5));
                    dto.setServicio(rs.getString(6));
                    dto.setCuenta(rs.getInt(7));
                    dto.setMonto(rs.getBigDecimal(8));
                    dto.setError(rs.getString(9));
                    return dto;
                });
        return list;
    }

}
