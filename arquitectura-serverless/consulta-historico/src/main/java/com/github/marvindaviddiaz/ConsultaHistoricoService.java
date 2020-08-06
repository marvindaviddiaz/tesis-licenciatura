package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.BitacoraDAO;
import com.github.marvindaviddiaz.dto.BitacoraDTO;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaHistoricoService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ConsultaHistoricoService.class.getName());
    private final BitacoraDAO dao = new BitacoraDAO();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        Gson gson = new Gson();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        Date fechaInicio = null;
        Date fechaFin = null;
        Integer pagina = null;
        String filtro = null;
        String responseJson;
        int statusCode = 201;
        try {
            fechaInicio = simpleDateFormat.parse(event.getQueryStringParameters().getOrDefault("fechaInicio", null));
            fechaFin = simpleDateFormat.parse(event.getQueryStringParameters().getOrDefault("fechaFin", null));
            pagina = Integer.valueOf(event.getQueryStringParameters().getOrDefault("pagina", null));
            filtro = event.getQueryStringParameters().getOrDefault("filtro", null);
            logger.log(Level.INFO, "User: {0}, Fechas: {1} {2}, Filtro={3} Pagina{4}", new Object[]{usuario, fechaInicio, fechaFin, filtro, pagina});

            List<BitacoraDTO> list = dao.consulta(Integer.parseInt(usuario), fechaInicio, fechaFin, filtro, pagina);
            responseJson = gson.toJson(list);

        } catch (ParseException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            responseJson = "{\"error\":\" " + e.getMessage() + "\"}";
            statusCode = 500;
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(responseJson);
    }
}
