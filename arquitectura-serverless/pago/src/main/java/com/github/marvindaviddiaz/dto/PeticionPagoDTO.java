package com.github.marvindaviddiaz.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PeticionPagoDTO {

    private Integer servicio;
    Map<String, String> identificadores = new HashMap<>();
    private Integer cuenta;

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Map<String, String> getIdentificadores() {
        return identificadores;
    }

    public void setIdentificadores(Map<String, String> identificadores) {
        this.identificadores = identificadores;
    }

    @Override
    public String toString() {
        return "PeticionPagoDTO{" +
                "servicio=" + servicio +
                ", identificadores=" + identificadores +
                ", cuenta=" + cuenta +
                '}';
    }
}
