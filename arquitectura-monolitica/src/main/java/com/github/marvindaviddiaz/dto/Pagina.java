package com.github.marvindaviddiaz.dto;

import java.io.Serializable;
import java.util.List;

public class Pagina<T> implements Serializable {

    private List<T> listado;
    private Long total;
    private Integer pagina;
    private Integer tamanoPagina;

    public Pagina(List<T> listado, Long total, Integer page, Integer tamanoPagina) {
        this.listado = listado;
        this.total = total;
        this.pagina = page;
        this.tamanoPagina = tamanoPagina;
    }

    public List<T> getListado() {
        return listado;
    }

    public void setListado(List<T> listado) {
        this.listado = listado;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getTamanoPagina() {
        return tamanoPagina;
    }

    public void setTamanoPagina(Integer tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }

    public boolean isAnterior() {
        return pagina > 1;
    }

    public boolean isSiguiente() {
        return (tamanoPagina * pagina) < total;
    }

    public Integer getTotalPaginas() {
        Integer totalPaginas = (total.intValue() / tamanoPagina);
        if (total.intValue() % tamanoPagina == 0) {
            return totalPaginas;
        } else {
            return totalPaginas + 1;
        }
    }


}
