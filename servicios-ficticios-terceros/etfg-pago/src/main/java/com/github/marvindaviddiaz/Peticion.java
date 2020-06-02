package com.github.marvindaviddiaz;

import java.math.BigDecimal;

public class Peticion {

    private String telefono;
    private BigDecimal valor;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
