package com.github.marvindaviddiaz.dto;

import java.util.Map;

public class ConsultaDTO {

    private Integer tercero;
    private Map<String, String> indentificadores;

    public Integer getTercero() {
        return tercero;
    }

    public void setTercero(Integer tercero) {
        this.tercero = tercero;
    }

    public Map<String, String> getIndentificadores() {
        return indentificadores;
    }

    public void setIndentificadores(Map<String, String> indentificadores) {
        this.indentificadores = indentificadores;
    }

    @Override
    public String toString() {
        return "Peticion{" +
                "tercero=" + tercero +
                ", indentificadores=" + indentificadores +
                '}';
    }
}
