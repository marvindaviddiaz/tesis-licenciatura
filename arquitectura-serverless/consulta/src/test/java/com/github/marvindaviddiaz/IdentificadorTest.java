package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Assert;
import org.junit.Test;

public class IdentificadorTest {

    @Test
    public void buscarServicioTest() {
        IdentificadorService servicio = new IdentificadorService();
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = servicio.handleRequest(null, null);
        Assert.assertTrue(apiGatewayProxyResponseEvent != null);
    }

}
