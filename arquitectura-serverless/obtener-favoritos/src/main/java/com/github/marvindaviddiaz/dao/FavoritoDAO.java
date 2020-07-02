package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.FavoritoDTO;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FavoritoDAO {

    private static final Logger logger = Logger.getLogger(FavoritoDAO.class.getName());
    private static final AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
    private static final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
            new DriverManagerDataSource(
                    String.format("jdbc:mysql://%s/%s",
                            getParameter(System.getenv("RDS_ENDPOINT"), false),
                            getParameter(System.getenv("RDS_DB_NAME"), false)),
                    getParameter(System.getenv("RDS_USERNAME"), false),
                    getParameter(System.getenv("RDS_PASSWORD"), true)));

    private static final String OBTENER_FAVORITOS = "select f.id, f.servicio, s.nombre, f.alias, ifa.identificador, ide.codigo, ifa.valor  from favorito f " +
            "    inner join identificador_favorito ifa on f.id = ifa.favorito " +
            "    inner join servicio s on f.servicio = s.id " +
            "    inner join identificador ide on ide.servicio = s.id " +
            "where f.usuario = :usuario";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public List<FavoritoDTO> obtenerIdentificadores(Integer usuario, Integer filtro) {
        List<FavoritoDTO> list = new ArrayList<>();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("usuario", usuario);
        String query = OBTENER_FAVORITOS;
        if (filtro != null) {
            query += " and f.id = :filtro";
            namedParameters = namedParameters.addValue("filtro", filtro);
        }
        jdbcTemplate.query(query, namedParameters,
                (rs, rowNum) -> {
                    FavoritoDTO dto = new FavoritoDTO();
                    dto.setId(rs.getInt(1));
                    dto.setServicioId(rs.getInt(2));
                    dto.setServicio(rs.getString(3));
                    dto.setAlias(rs.getString(4));
                    int i = list.indexOf(dto);
                    if (i != -1) {
                        dto = list.get(i);
                    } else {
                        list.add(dto);
                    }

                    IdentificadorDTO identificador = new IdentificadorDTO();
                    identificador.setId(rs.getInt(5));
                    identificador.setCodigo(rs.getString(6));
                    identificador.setValor(rs.getString(7));

                    dto.getIdentificadores().add(identificador);
                    return dto;
                });
        return list;
    }

}
