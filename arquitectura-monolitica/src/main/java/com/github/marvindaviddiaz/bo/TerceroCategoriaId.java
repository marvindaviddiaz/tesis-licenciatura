package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class TerceroCategoriaId implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @ManyToOne
    @JoinColumn(name = "tercero")
    private Tercero tercero;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    public Tercero getTercero() {
        return tercero;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
