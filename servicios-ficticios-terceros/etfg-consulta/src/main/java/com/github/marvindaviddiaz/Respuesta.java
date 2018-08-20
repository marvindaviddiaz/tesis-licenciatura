package com.github.marvindaviddiaz;

import java.math.BigDecimal;
import java.util.UUID;

public class Respuesta {

    private UUID id;
    private BigDecimal saldo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
