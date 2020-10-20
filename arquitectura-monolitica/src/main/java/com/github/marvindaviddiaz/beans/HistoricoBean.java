package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Bitacora;
import com.github.marvindaviddiaz.dto.Pagina;
import com.github.marvindaviddiaz.service.BitacoraService;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@ViewScoped
@Named("historicoBean")
public class HistoricoBean implements Serializable {

    @Inject
    private transient BitacoraService bitacoraService;

    private Date inicio;
    private Date fin;
    private String busqueda;
    private Pagina<Bitacora> pagina;
    private Integer paginaActual;
    private static final Integer REGISTROS_POR_PAGINA = 10;

    private Integer usuario = 36; // TODO

    public void preRenderView() {
        if (inicio == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -30);
            inicio = calendar.getTime();
            fin = new Date();
            buscar();
        }
    }

    public void buscar() {
        paginaActual = 1;
        pagina = bitacoraService.buscar(usuario, inicio, fin, busqueda, paginaActual, REGISTROS_POR_PAGINA);
    }

    public void anterior() {
        paginaActual = paginaActual - 1;
        pagina = bitacoraService.buscar(usuario, inicio, fin, busqueda, paginaActual, REGISTROS_POR_PAGINA);
    }

    public void siguiente() {
        paginaActual = paginaActual + 1;
        pagina = bitacoraService.buscar(usuario, inicio, fin, busqueda, paginaActual, REGISTROS_POR_PAGINA);
    }

    public Pagina<Bitacora> getPagina() {
        return pagina;
    }

    public void setPagina(Pagina<Bitacora> pagina) {
        this.pagina = pagina;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

}
