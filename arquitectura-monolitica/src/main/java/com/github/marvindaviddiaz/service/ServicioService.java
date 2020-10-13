package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.dao.ServicioDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.List;

@Singleton
public class ServicioService implements Serializable {

    @Inject
    private ServicioDao dao;

    public Servicio obtenerServicio(Integer servicioId) {
        return dao.obtenerServicio(servicioId);
    }

    public List<Servicio> buscarServicio(String busqueda) {
        return dao.buscarServicio(busqueda);
    }
}
