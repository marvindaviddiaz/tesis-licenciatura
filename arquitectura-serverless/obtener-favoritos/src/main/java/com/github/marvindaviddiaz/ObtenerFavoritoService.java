package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.FavoritoDAO;
import com.github.marvindaviddiaz.dto.FavoritoDTO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObtenerFavoritoService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ObtenerFavoritoService.class.getName());
    private final FavoritoDAO dao = new FavoritoDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        Gson gson = new Gson();
        logger.log(Level.INFO, "User: {0}, Petición", new Object[]{usuario});

        String f = event.getQueryStringParameters().getOrDefault("filtro", null);
        Integer filtro = null;
        if (f != null) {
            filtro = Integer.parseInt(f);
        }
        String responseJson;
        int statusCode = 201;
        try {
            List<FavoritoDTO> list = dao.obtenerIdentificadores(Integer.parseInt(usuario), filtro);
            responseJson = gson.toJson(list);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            responseJson = "{\"error\":\" " + e.getMessage() + "\"}";
            statusCode = 500;
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(responseJson);
    }
}
