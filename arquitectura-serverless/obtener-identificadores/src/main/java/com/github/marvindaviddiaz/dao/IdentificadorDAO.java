package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.dto.IdentificadorDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class IdentificadorDAO {

    private static NamedParameterJdbcTemplate jdbcTemplate;

    private static final String QUERY = "select obj.id, obj.nombre, obj.tipo " +
            "from identificador obj " +
            "where obj.servicio = :servicio ";

    static {
        jdbcTemplate = new NamedParameterJdbcTemplate(
                new DriverManagerDataSource(
                        String.format("jdbc:mysql://%s/%s", System.getenv("RDS_ENDPOINT"), System.getenv("RDS_DB_NAME")),
                        System.getenv("RDS_USERNAME"),
                        System.getenv("RDS_PASSWORD")));
    }

    public List<IdentificadorDTO> buscarIdentificador(Integer servicio) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("servicio", servicio);
        return jdbcTemplate.query(QUERY, namedParameters,
                (rs, rowNum) -> {
                    IdentificadorDTO p = new IdentificadorDTO();
                    p.setId(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    p.setTipo(rs.getString(3));
                    return p;
                });
    }

}
