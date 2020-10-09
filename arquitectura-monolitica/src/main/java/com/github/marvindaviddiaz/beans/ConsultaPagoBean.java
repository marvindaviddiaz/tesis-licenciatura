package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.dto.ConsultaPagoDTO;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;
import com.github.marvindaviddiaz.service.ConsultaService;
import com.github.marvindaviddiaz.service.IdentificadorService;
import com.github.marvindaviddiaz.service.ServicioService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ViewScoped
@Named("consultaPagoBean")
public class ConsultaPagoBean implements Serializable {

    @Inject
    private ServicioService servicioService;
    @Inject
    private IdentificadorService identificadorService;
    @Inject
    private ConsultaService consultaService;

    private Servicio servicio;
    private Integer servicioId;
    private List<IdentificadorDTO> identificadores;
    private BigDecimal monto;

    private boolean paso1 = true;
    private boolean paso2 = false;
    private boolean paso3 = false;


    @PostConstruct
    public void init() {
    }

    public void preRenderView() {
        if (servicio == null || !servicio.getId().equals(servicioId)) {
            servicio = servicioService.obtenerServicio(servicioId);
            identificadores = identificadorService.obtenerIdentificadores(servicioId);
        }
    }


    public void consultar() {
        if (identificadores != null && identificadores.stream().anyMatch(i -> i.getValor() != null && !i.getValor().isEmpty())) {
            System.out.println(identificadores);
            ConsultaPagoDTO consultar = consultaService.consultar(servicio, identificadores);
            monto = consultar.getSaldo();
            paso2 = true;
            paso1 = false;
        }
    }


    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    public List<IdentificadorDTO> getIdentificadores() {
        return identificadores;
    }

    public void setIdentificadores(List<IdentificadorDTO> identificadores) {
        this.identificadores = identificadores;
    }

    public boolean isPaso1() {
        return paso1;
    }

    public void setPaso1(boolean paso1) {
        this.paso1 = paso1;
    }

    public boolean isPaso2() {
        return paso2;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setPaso2(boolean paso2) {
        this.paso2 = paso2;
    }

    public boolean isPaso3() {
        return paso3;
    }

    public void setPaso3(boolean paso3) {
        this.paso3 = paso3;
    }


}
