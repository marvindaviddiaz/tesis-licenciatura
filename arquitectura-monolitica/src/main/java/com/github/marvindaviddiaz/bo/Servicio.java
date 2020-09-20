package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "tercero")
    private Tercero tercero;

    @Column
    private String nombre;

    @Column
    private String estado;

    @Column
    private String tipo;

    @Column
    private Integer reintentos;

    @Column(name = "tiempo_maximo_respuesta")
    private Integer tiempoMaximoRespuesta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public Integer getTiempoMaximoRespuesta() {
        return tiempoMaximoRespuesta;
    }

    public void setTiempoMaximoRespuesta(Integer tiempoMaximoRespuesta) {
        this.tiempoMaximoRespuesta = tiempoMaximoRespuesta;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", tercero=" + tercero +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", reintentos=" + reintentos +
                ", tiempoMaximoRespuesta=" + tiempoMaximoRespuesta +
                '}';
    }
}
