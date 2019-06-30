package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Assert;
import org.junit.Test;

public class ListarCuentasTest {

    @Test
    public void listarCuentasTest() {
        CuentaService servicio = new CuentaService();
        APIGatewayProxyResponseEvent cuentas = servicio.handleRequest(null, new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return new CognitoIdentity() {
                    @Override
                    public String getIdentityId() {
                        return "1";
                    }

                    @Override
                    public String getIdentityPoolId() {
                        return null;
                    }
                };
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        });
        Assert.assertTrue(cuentas != null && cuentas.getStatusCode() == 200);
    }

}
