package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.BitacoraDTO;
import com.github.marvindaviddiaz.dto.PageDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.text.SimpleDateFormat;
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

    private static final String BITACORA_COUNT = "select count(0) from (%s) T";
    private static final String BITACORA = "select id, fecha, estado, tipo, tercero, servicio, cuenta, monto, mensaje_error " +
            " from bitacora where usuario = :usuario and fecha between :inicio and :fin";
    private static final Integer MAX_ELEMENTS = 10;

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public PageDTO<BitacoraDTO> consulta(Integer usuario, Date fechaInicio, Date fechaFin, String filtro, Integer pagina) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("inicio", fechaInicio)
                .addValue("fin", fechaFin);
        String query = BITACORA;
        if (filtro != null && !filtro.trim().isEmpty()) {
            query += " and (lower(tercero) like :filtro or lower(servicio) like :filtro)";
            namedParameters = namedParameters.addValue("filtro", '%' + filtro.toLowerCase() + '%');
        }

        String count = String.format(BITACORA_COUNT, BITACORA);
        query += " order by fecha desc limit " + (pagina * MAX_ELEMENTS) + ", " + MAX_ELEMENTS;
        Long total = jdbcTemplate.queryForObject(count, namedParameters, Long.class);
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        List<BitacoraDTO> content = jdbcTemplate.query(query, namedParameters,
                (rs, rowNum) -> {
                    BitacoraDTO dto = new BitacoraDTO();
                    dto.setId(rs.getString(1));
                    dto.setFecha(fecha.format(rs.getDate(2)));
                    dto.setEstado(rs.getString(3));
                    dto.setTipo(rs.getString(4));
                    dto.setTercero(rs.getString(5));
                    dto.setServicio(rs.getString(6));
                    dto.setCuenta(rs.getInt(7));
                    dto.setMonto(rs.getBigDecimal(8));
                    dto.setError(rs.getString(9));
                    return dto;
                });

        return new PageDTO<>(content, total, pagina, MAX_ELEMENTS);
    }

}
