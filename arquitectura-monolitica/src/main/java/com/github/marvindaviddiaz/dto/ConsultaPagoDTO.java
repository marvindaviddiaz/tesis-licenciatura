package com.github.marvindaviddiaz.dto;

import java.math.BigDecimal;

public class ConsultaPagoDTO {

    private String id;
    private BigDecimal saldo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
