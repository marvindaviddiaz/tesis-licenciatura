package com.github.marvindaviddiaz.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "servicio")
public class Servicio {

    private Integer id;
    private String nombre;
    private Boolean estado;
    private String tipo;
    private Integer reintentos;
    private Integer tiempoMaximoRespuesta;

    @Id
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getReintentos() {
        return reintentos;
    }

    public void setReintentos(Integer reintentos) {
        this.reintentos = reintentos;
    }

    @Column(name = "tiempo_maximo_respuesta")
    public Integer getTiempoMaximoRespuesta() {
        return tiempoMaximoRespuesta;
    }

    public void setTiempoMaximoRespuesta(Integer tiempoMaximoRespuesta) {
        this.tiempoMaximoRespuesta = tiempoMaximoRespuesta;
    }
}
