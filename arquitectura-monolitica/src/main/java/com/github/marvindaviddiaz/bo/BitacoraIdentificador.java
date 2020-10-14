package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bitacora_identificador")
public class BitacoraIdentificador implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @EmbeddedId
    private BitacoraIdentificadorId id;

    @Column
    private String valor;


    public BitacoraIdentificadorId getId() {
        return id;
    }

    public void setId(BitacoraIdentificadorId id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
