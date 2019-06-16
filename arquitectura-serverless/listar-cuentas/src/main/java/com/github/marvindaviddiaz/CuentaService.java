package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.marvindaviddiaz.dao.CuentaDAO;
import com.github.marvindaviddiaz.dto.CuentaDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaService implements RequestHandler<Object, List<CuentaDTO>> {

    private static final Logger logger = Logger.getLogger(CuentaService.class.getName());
    private CuentaDAO cuentaDAO = new CuentaDAO();

    @Override
    public List<CuentaDTO> handleRequest(Object object, Context context) {
        logger.log(Level.INFO, "Contexto: {0}", context);
        Integer usuario = Integer.valueOf(context.getIdentity().getIdentityId());
        return cuentaDAO.listaCuentas(usuario);
    }

}
