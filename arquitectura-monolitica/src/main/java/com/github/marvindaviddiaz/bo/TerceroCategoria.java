package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tercero_categoria")
public class TerceroCategoria implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @EmbeddedId
    TerceroCategoriaId terceroCategoriaId;

    public TerceroCategoriaId getTerceroCategoriaId() {
        return terceroCategoriaId;
    }

    public void setTerceroCategoriaId(TerceroCategoriaId terceroCategoriaId) {
        this.terceroCategoriaId = terceroCategoriaId;
    }
}
