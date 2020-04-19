package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.ConsultaDAO;
import com.github.marvindaviddiaz.dto.ConsultaDTO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ConsultaService.class.getName());
    private ConsultaDAO dao = new ConsultaDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        Integer servicio = Integer.parseInt(event.getPathParameters().getOrDefault("servicio", "-1"));
        Map<String, String> params = event.getQueryStringParameters();
        logger.log(Level.INFO, "User: {0}, Servicio:  {1}, Params: {2}", new Object[]{usuario, servicio, params});
//        List<ConsultaDTO> servicioDTOS = dao.buscarIdentificador(servicio);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(new Gson().toJson(""));
    }
}
