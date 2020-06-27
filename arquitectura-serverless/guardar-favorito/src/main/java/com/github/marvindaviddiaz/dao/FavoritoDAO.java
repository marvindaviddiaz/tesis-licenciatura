package com.github.marvindaviddiaz.dao;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.github.marvindaviddiaz.dto.PeticionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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

    private static final String INSERT_FAVORITO = "insert into favorito(usuario, servicio, alias) " +
            "values(:usuario, :servicio, :alias) ";

    private static final String INSERT_IDENTIFICADOR_FAVORITO = "insert into identificador_favorito(favorito, identificador, valor) " +
            "values(:favorito, :identificador, :valor) ";

    private static String getParameter(String parameterName, Boolean decryption) {
        logger.log(Level.INFO, "Getting param: {0}", parameterName);
        return ssm.getParameter(new GetParameterRequest()
                .withName(parameterName)
                .withWithDecryption(decryption)
        ).getParameter().getValue();
    }

    public void guardarFavorito(Integer usuario, PeticionDTO dto) {
        // TODO: run all on a transaction
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("servicio", dto.getServicio())
                .addValue("alias", dto.getAlias());
        jdbcTemplate.update(INSERT_FAVORITO, namedParameters, keyHolder);

        dto.getIdentificadores().forEach((identificador, valor) ->
                jdbcTemplate.update(INSERT_IDENTIFICADOR_FAVORITO, new MapSqlParameterSource()
                        .addValue("favorito", keyHolder.getKey())
                        .addValue("identificador", identificador)
                        .addValue("valor", valor))
        );

    }

}
