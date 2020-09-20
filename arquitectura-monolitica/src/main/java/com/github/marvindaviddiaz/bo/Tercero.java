package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tercero")
public class Tercero implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
