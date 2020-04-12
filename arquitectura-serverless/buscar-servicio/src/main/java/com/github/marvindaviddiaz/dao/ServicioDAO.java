package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.ServicioDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioDAO {

    private static final Logger logger = Logger.getLogger(ServicioDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));


    private static final String BUSQUEDA_SERVICIOS_QUERY = "select s.id, s.nombre, t.id, t.nombre " +
            "from servicio s inner join tercero t on s.tercero = t.id inner join palabra_clave pc on t.id = pc.tercero inner join tercero_categoria tc on t.id = tc.tercero inner join categoria c on tc.categoria = c.id " +
            "where lower(s.nombre) like :busqueda or lower(t.nombre) like :busqueda or lower(pc.palabra_clave) like :busqueda or lower(c.nombre) like :busqueda " +
            "group by s.id, s.nombre, t.id, t.nombre";

    private static String getParameter(String parameterName, Boolean decryption) {
        try {
            logger.log(Level.INFO, "Getting param: {0}", parameterName);
            return ssm.getParameter(new GetParameterRequest()
                    .withName(parameterName)
                    .withWithDecryption(decryption)
            ).getParameter().getValue();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
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
