package com.github.marvindaviddiaz.dto;

import java.util.HashMap;
import java.util.Map;

public class PeticionDTO {

    private Integer servicio;
    private String alias;
    Map<Integer, String> identificadores = new HashMap<>();

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    public Map<Integer, String> getIdentificadores() {
        return identificadores;
    }

    public void setIdentificadores(Map<Integer, String> identificadores) {
        this.identificadores = identificadores;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "PeticionDTO{" +
                "servicio=" + servicio +
                ", alias='" + alias + '\'' +
                ", identificadores=" + identificadores +
                '}';
    }
}
