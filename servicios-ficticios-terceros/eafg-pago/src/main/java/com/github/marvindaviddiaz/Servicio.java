package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.UUID;

public class Servicio implements RequestHandler<Peticion, Respuesta> {

    @Override
    public Respuesta handleRequest(Peticion peticion, Context context) {
        Respuesta respuesta = new Respuesta();
        respuesta.setId(UUID.randomUUID());

        return respuesta;
    }
}