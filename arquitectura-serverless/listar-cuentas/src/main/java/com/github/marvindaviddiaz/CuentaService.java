package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.github.marvindaviddiaz.dao.CuentaDAO;
import com.github.marvindaviddiaz.dto.CuentaDTO;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaService implements RequestHandler<APIGatewayProxyRequestEvent, List<CuentaDTO>> {

    private static final Logger logger = Logger.getLogger(CuentaService.class.getName());
    private CuentaDAO cuentaDAO = new CuentaDAO();

    @Override
    public List<CuentaDTO> handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        logger.log(Level.INFO, "Authorizer: {0}", event.getRequestContext().getAuthorizer());
        String usuario = (String) ((Map)event.getRequestContext().getAuthorizer().get("claims")).get("cognito:username");
        return cuentaDAO.listaCuentas(Integer.valueOf(usuario));
    }

}
