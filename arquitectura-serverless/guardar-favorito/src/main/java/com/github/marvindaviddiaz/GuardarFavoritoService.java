package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.FavoritoDAO;
import com.github.marvindaviddiaz.dto.PeticionDTO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuardarFavoritoService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(GuardarFavoritoService.class.getName());
    private FavoritoDAO dao = new FavoritoDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        Gson gson = new Gson();
        PeticionDTO peticion = gson.fromJson(event.getBody(), PeticionDTO.class);
        logger.log(Level.INFO, "User: {0}, Petición:  {1}", new Object[]{usuario, peticion});

        String responseJson = "{}";
        int statusCode = 201;
        try {
            dao.guardarFavorito(Integer.parseInt(usuario), peticion);
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
