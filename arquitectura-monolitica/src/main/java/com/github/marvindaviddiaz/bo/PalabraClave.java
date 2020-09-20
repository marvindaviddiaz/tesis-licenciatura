package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "palabra_clave")
public class PalabraClave implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tercero")
    private Tercero tercero;

    @Column(name = "palabra_clave")
    private String palabraClave;

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

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }
}
