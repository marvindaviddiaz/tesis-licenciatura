package com.github.marvindaviddiaz.dto;

import com.github.marvindaviddiaz.bo.Identificador;

import java.io.Serializable;

public class IdentificadorDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String codigo;
    private String valor;

    public IdentificadorDTO(Identificador i) {
        this.id = i.getId();
        this.codigo = i.getCodigo();
        this.nombre = i.getNombre();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
