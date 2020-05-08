package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.InterfazDAO;
import com.github.marvindaviddiaz.dto.InterfazDTO;
import org.apache.commons.text.StringSubstitutor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ConsultaService.class.getName());
    private InterfazDAO dao = new InterfazDAO();

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer:  {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        Integer servicio = Integer.parseInt(event.getPathParameters().getOrDefault("servicio", "-1"));
        Map<String, String> params = event.getQueryStringParameters();
        logger.log(Level.INFO, "User: {0}, Servicio:  {1}, Params: {2}", new Object[]{usuario, servicio, params});
        InterfazDTO interfaz = dao.obtenerInterfaz(servicio, "C");

        StringSubstitutor sub = new StringSubstitutor(params);
        String mensaje = sub.replace(interfaz.getMensaje());
        logger.log(Level.INFO, mensaje);

        String responseJson = "";
        int statusCode = 500;
        if ("REST".equals(interfaz.getProtocolo())) {
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .method(interfaz.getMetodo(), HttpRequest.BodyPublishers.ofString(mensaje))
                        .uri(URI.create(interfaz.getUrl()))
                        .setHeader("Content-Type", "application/json")
                        .timeout(Duration.ofMillis(interfaz.getTimeout()))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                logger.log(Level.INFO, "Respuesta {0}, {1}", new Object[]{response.statusCode(), response.body()});
                responseJson = response.body();
                statusCode = response.statusCode();
            } catch (Exception e) {
                // TODO: REINTENTOS
                responseJson = "{\"error\":\" " + e.getMessage() + "\"}";
            }
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(responseJson);
    }
}
