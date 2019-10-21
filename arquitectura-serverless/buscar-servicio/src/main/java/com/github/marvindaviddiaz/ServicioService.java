package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.ServicioDAO;
import com.github.marvindaviddiaz.dto.ServicioDTO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ServicioService.class.getName());
    private ServicioDAO servicioDAO = new ServicioDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        logger.log(Level.INFO, "User:  {0}", usuario);
        String search = new Gson().fromJson(event.getBody(), String.class);
        List<ServicioDTO> servicioDTOS = servicioDAO.buscarServicio(search);



        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(new Gson().toJson(servicioDTOS));
    }
}
