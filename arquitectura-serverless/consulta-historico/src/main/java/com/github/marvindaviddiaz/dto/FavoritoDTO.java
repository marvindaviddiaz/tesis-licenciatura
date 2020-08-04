package com.github.marvindaviddiaz.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FavoritoDTO {

    private Integer id;
    private Integer servicioId;
    private String servicio;
    private String alias;
    private List<IdentificadorDTO> identificadores = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<IdentificadorDTO> getIdentificadores() {
        return identificadores;
    }

    public void setIdentificadores(List<IdentificadorDTO> identificadores) {
        this.identificadores = identificadores;
    }

    @Override
    public String toString() {
        return "FavoritoDTO{" +
                "id=" + id +
                ", servicioId=" + servicioId +
                ", servicio='" + servicio + '\'' +
                ", alias='" + alias + '\'' +
                ", identificadores=" + identificadores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoDTO that = (FavoritoDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(servicioId, that.servicioId) &&
                Objects.equals(servicio, that.servicio) &&
                Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, servicioId, servicio, alias);
    }
}
