package com.github.marvindaviddiaz.bo;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class IdentificadorFavoritoId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "favorito")
    private Favorito favorito;
    @ManyToOne
    @JoinColumn(name = "identificador")
    private Identificador identificador;

    public Favorito getFavorito() {
        return favorito;
    }

    public void setFavorito(Favorito favorito) {
        this.favorito = favorito;
    }

    public Identificador getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Identificador identificador) {
        this.identificador = identificador;
    }
}
