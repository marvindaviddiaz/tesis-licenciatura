package com.github.marvindaviddiaz.dto;

public class InterfazDTO {

    private String url;
    private String protocolo;
    private String metodo;
    private String mensaje;
    private Integer reintentos;
    private Integer timeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getReintentos() {
        return reintentos;
    }

    public void setReintentos(Integer reintentos) {
        this.reintentos = reintentos;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
