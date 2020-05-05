package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.github.marvindaviddiaz.dao.InterfazDAO;
import com.github.marvindaviddiaz.dto.InterfazDTO;
import com.google.gson.Gson;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = Logger.getLogger(ConsultaService.class.getName());
    private InterfazDAO dao = new InterfazDAO();

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

        if ("REST".equals(interfaz.getProtocolo())) {
            try{
                URL url = new URL(interfaz.getUrl());
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod(interfaz.getMetodo());
                httpConnection.setReadTimeout(interfaz.getTimeout());
                httpConnection.setConnectTimeout(interfaz.getTimeout());
                InputStream is = httpConnection.getInputStream();
                String respuesta = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                logger.log(Level.INFO, "Respuesta {0}, {1}", new Object[]{httpConnection.getResponseCode(), respuesta});
            } catch (Exception e) {
                // TODO: RETURN ERROR
                // TODO: REINTENTOS
                e.printStackTrace();
            }
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                .withBody(new Gson().toJson(""));
    }
}
