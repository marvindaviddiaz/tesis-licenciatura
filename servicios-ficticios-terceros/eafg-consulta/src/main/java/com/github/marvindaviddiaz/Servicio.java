package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servicio implements RequestHandler<Peticion, Respuesta> {

    private static final Logger logger = Logger.getLogger(Servicio.class.getName());

    @Override
    public Respuesta handleRequest(Peticion peticion, Context context) {
        logger.log(Level.INFO, "{0} Petición: {1}, Contexto: {2}", new Object[]{context.getAwsRequestId(), peticion, context});
        Respuesta respuesta = new Respuesta();
        respuesta.setId(UUID.randomUUID());
        respuesta.setSaldo(BigDecimal.valueOf(125.50));

        return respuesta;
    }
}