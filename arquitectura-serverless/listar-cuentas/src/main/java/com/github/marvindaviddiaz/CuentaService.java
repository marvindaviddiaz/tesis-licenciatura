package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.CuentaDAO;
import com.github.marvindaviddiaz.dto.CuentaDTO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(CuentaService.class.getName());
    private final CuentaDAO cuentaDAO = new CuentaDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer: {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        logger.log(Level.INFO, "User: {0}", usuario);
        List<CuentaDTO> cuentaDTOS = cuentaDAO.listaCuentas(Integer.valueOf(usuario));
        logger.log(Level.INFO, "Result: {0}", cuentaDTOS);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(new Gson().toJson(cuentaDTOS));
    }
}
