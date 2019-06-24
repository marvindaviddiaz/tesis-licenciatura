package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.github.marvindaviddiaz.dao.CuentaDAO;
import com.github.marvindaviddiaz.dto.CuentaDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaService implements RequestHandler<APIGatewayProxyRequestEvent, List<CuentaDTO>> {

    private static final Logger logger = Logger.getLogger(CuentaService.class.getName());
    private CuentaDAO cuentaDAO = new CuentaDAO();

    @Override
    public List<CuentaDTO> handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Contexto: {0}", context);
        logger.log(Level.INFO, "Identity: {0}", context.getIdentity());
        logger.log(Level.INFO, "Pool: {0}", context.getIdentity().getIdentityPoolId());
        logger.log(Level.INFO, "CognitoAuthenticationProvider: {0}", event.getRequestContext().getIdentity().getCognitoAuthenticationProvider());
        logger.log(Level.INFO, "CognitoAuthenticationProviderType: {0}", event.getRequestContext().getIdentity().getCognitoAuthenticationType());
        logger.log(Level.INFO, "CognitoIdentityId: {0}", event.getRequestContext().getIdentity().getCognitoIdentityId());
        logger.log(Level.INFO, "CognitoIdentityPoolId: {0}", event.getRequestContext().getIdentity().getCognitoIdentityPoolId());
        logger.log(Level.INFO, "User: {0}", event.getRequestContext().getIdentity().getUser());
        logger.log(Level.INFO, "Authorizer: {0}", event.getRequestContext().getAuthorizer());
        Integer usuario = Integer.valueOf(event.getRequestContext().getIdentity().getCognitoIdentityId());
        return cuentaDAO.listaCuentas(usuario);
    }

}
