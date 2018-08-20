package com.github.marvindaviddiaz;

import java.math.BigDecimal;

public class Peticion {

    private String contador;
    private BigDecimal saldo;

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
