package com.github.marvindaviddiaz.dto;

public class IdentificadorDTO {

    private Integer id;
    private String codigo;
    private String valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "IdentificadorDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
