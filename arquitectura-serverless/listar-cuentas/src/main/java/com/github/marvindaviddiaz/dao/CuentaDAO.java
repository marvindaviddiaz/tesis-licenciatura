package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.dto.CuentaDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class CuentaDAO {

    private static NamedParameterJdbcTemplate jdbcTemplate;

    private static final String LISTAR_CUENTAS_QUERY = "select numero, usuario, tipo, alias, estado, saldo_actual," +
            "saldo_disponible, moneda from cuenta where usuario = :usuario and estado = :estado and moneda = :moneda";

    static {
        jdbcTemplate = new NamedParameterJdbcTemplate(
                new DriverManagerDataSource(
                        String.format("jdbc:mysql://%s/%s", System.getenv("RDS_ENDPOINT"), System.getenv("RDS_DB_NAME")),
                        System.getenv("RDS_USERNAME"),
                        System.getenv("RDS_PASSWORD")));
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
                    p.setUsuario(rs.getInt(2));
                    p.setTipo(rs.getString(3));
                    p.setAlias(rs.getString(4));
                    p.setEstado(rs.getString(5));
                    p.setSaldoActual(rs.getBigDecimal(6));
                    p.setSaldoDisponible(rs.getBigDecimal(7));
                    return p;
                });
    }

}
