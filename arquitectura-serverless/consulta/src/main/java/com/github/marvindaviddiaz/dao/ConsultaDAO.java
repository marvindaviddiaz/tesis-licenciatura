package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.ConsultaDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO {

    private static final Logger logger = Logger.getLogger(ConsultaDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));

    private static final String QUERY = "select obj.id, obj.nombre, obj.tipo, obj.codigo " +
            "from identificador obj " +
            "where obj.servicio = :servicio ";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public List<ConsultaDTO> buscarIdentificador(Integer servicio) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("servicio", servicio);
        return jdbcTemplate.query(QUERY, namedParameters,
                (rs, rowNum) -> {
                    ConsultaDTO p = new ConsultaDTO();
                    p.setId(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    p.setTipo(rs.getString(3));
                    p.setCodigo(rs.getString(4));
                    return p;
                });
    }

}
