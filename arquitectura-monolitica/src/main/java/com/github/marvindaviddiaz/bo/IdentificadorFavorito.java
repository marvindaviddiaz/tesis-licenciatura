package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "identificador_favorito")
public class IdentificadorFavorito implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @EmbeddedId
    private IdentificadorFavoritoId id;

    @Column
    private String valor;

    public IdentificadorFavoritoId getId() {
        return id;
    }

    public void setId(IdentificadorFavoritoId id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
