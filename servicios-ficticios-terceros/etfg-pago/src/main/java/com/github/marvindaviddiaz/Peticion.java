package com.github.marvindaviddiaz;

import java.math.BigDecimal;

public class Peticion {

    private String telefono;
    private BigDecimal saldo;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
