package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Assert;
import org.junit.Test;

public class BuscarServicioTest {

    @Test
    public void buscarServicioTest() {
        ServicioService servicio = new ServicioService();
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = servicio.handleRequest(null, null);
        Assert.assertTrue(apiGatewayProxyResponseEvent != null);
    }

}
