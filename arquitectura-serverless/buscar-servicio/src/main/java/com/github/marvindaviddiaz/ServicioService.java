package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.marvindaviddiaz.dao.ServicioDAO;
import com.github.marvindaviddiaz.dto.ServicioDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioService implements RequestHandler<String, List<ServicioDTO>> {

    private static final Logger logger = Logger.getLogger(ServicioService.class.getName());
    private ServicioDAO servicioDAO = new ServicioDAO();

    @Override
    public List<ServicioDTO> handleRequest(String busqueda, Context context) {
        logger.log(Level.INFO, "Búsqueda: {0}, Contexto: {1}", new Object[]{busqueda, context});
        return servicioDAO.buscarServicio(busqueda);
    }

}
