package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class BitacoraIdentificadorId implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Column
    private String bitacora;
    @Column
    private String identificador;

    public String getBitacora() {
        return bitacora;
    }

    public void setBitacora(String bitacora) {
        this.bitacora = bitacora;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
