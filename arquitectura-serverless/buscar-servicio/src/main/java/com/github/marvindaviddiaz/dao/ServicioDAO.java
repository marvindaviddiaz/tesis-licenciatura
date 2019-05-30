package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.dto.ServicioDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class ServicioDAO {

    private static NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BUSQUEDA_SERVICIOS_QUERY = "select s.id, s.nombre, t.id, t.nombre " +
            "from servicio s inner join tercero t on s.tercero = t.id inner join palabra_clave pc on t.id = pc.tercero inner join tercero_categoria tc on t.id = tc.tercero inner join categoria c on tc.categoria = c.id " +
            "where lower(s.nombre) like :busqueda or lower(t.nombre) like :busqueda or lower(pc.palabra_clave) like :busqueda or lower(c.nombre) like :busqueda " +
            "group by s.id, s.nombre, t.id, t.nombre;";

    static {
        jdbcTemplate = new NamedParameterJdbcTemplate(
                new DriverManagerDataSource(
                        String.format("jdbc:mysql://%s/%s", System.getenv("RDS_ENDPOINT"), System.getenv("RDS_DB_NAME")),
                        System.getenv("RDS_USERNAME"),
                        System.getenv("RDS_PASSWORD")));
    }

    public List<ServicioDTO> buscarServicio(String busqueda) {
        busqueda = "%".concat(busqueda.toLowerCase()).concat("%");
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("busqueda", busqueda);
        return jdbcTemplate.query(BUSQUEDA_SERVICIOS_QUERY, namedParameters,
                (rs, rowNum) -> {
                    ServicioDTO p = new ServicioDTO();
                    p.setIdServicio(rs.getInt(1));
                    p.setNombreServicio(rs.getString(2));
                    p.setIdTercero(rs.getInt(3));
                    p.setNombreTercero(rs.getString(4));
                    return p;
                });
    }

}
