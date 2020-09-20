package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "identificador")
public class Identificador implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "servicio")
    private Servicio servicio;

    @Column
    private String nombre;

    @Column
    private String tipo;

    @Column
    private String codigo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
