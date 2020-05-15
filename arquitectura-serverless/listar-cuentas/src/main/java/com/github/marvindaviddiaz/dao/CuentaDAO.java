package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.CuentaDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaDAO {

    private static final Logger logger = Logger.getLogger(CuentaDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));

    private static final String LISTAR_CUENTAS_QUERY = "select numero, tipo, alias, estado, saldo_actual," +
            "saldo_disponible, moneda from cuenta where usuario = :usuario and estado = :estado and moneda = :moneda";


    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public List<CuentaDTO> listaCuentas(Integer usuario) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("estado", "A")
                .addValue("moneda", "GTQ");
        return jdbcTemplate.query(LISTAR_CUENTAS_QUERY, namedParameters,
                (rs, rowNum) -> {
                    CuentaDTO p = new CuentaDTO();
                    p.setNumero(rs.getInt(1));
                    p.setTipo(rs.getString(2));
                    p.setAlias(rs.getString(3));
                    p.setEstado(rs.getString(4));
                    p.setSaldoActual(rs.getBigDecimal(5));
                    p.setSaldoDisponible(rs.getBigDecimal(6));
                    return p;
                });
    }

}
