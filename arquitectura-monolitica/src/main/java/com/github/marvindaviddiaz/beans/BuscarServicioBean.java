package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Favorito;
import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.service.FavoritoService;
import com.github.marvindaviddiaz.service.ServicioService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("buscarServicioBean")
public class BuscarServicioBean implements Serializable {

    @Inject
    private transient ServicioService servicioService;
    @Inject
    private transient FavoritoService favoritoService;

    private List<Servicio> listado;
    private List<Favorito> favoritos;

    @PostConstruct
    public void init() {
        favoritos = favoritoService.obtenerFavoritos(36); // TODO: usuario
    }

    public void buscarServicio(String busqueda) {
        if (busqueda != null && !busqueda.isEmpty()) {
            listado = servicioService.buscarServicio(busqueda);
        }
    }

    public List<Servicio> getListado() {
        return listado;
    }

    public void setListado(List<Servicio> listado) {
        this.listado = listado;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }
}
