package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.service.ServicioService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("buscarServicioBean")
public class BuscarServicioBean implements Serializable {

    @Inject
    private ServicioService indexService;

    private List<Servicio> listado;

    public void buscarServicio(String busqueda) {
        if (busqueda != null && !busqueda.isEmpty()) {
            listado = indexService.buscarServicio(busqueda);
        }
    }

    public List<Servicio> getListado() {
        return listado;
    }

    public void setListado(List<Servicio> listado) {
        this.listado = listado;
    }
}
