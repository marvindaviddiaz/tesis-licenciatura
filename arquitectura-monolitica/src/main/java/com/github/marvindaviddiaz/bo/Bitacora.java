package com.github.marvindaviddiaz.bo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bitacora")
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 5107982536144833949L;

    @Id
    private String id;
    @Column
    private Date fecha;
    @Column
    private Integer usuario;
    @Column
    private String estado;
    @Column
    private String tipo;
    @Column
    private String tercero;
    @Column
    private String servicio;
    @Column
    private Integer cuenta;
    @Column
    private BigDecimal monto;
    @Column(name = "respuesta_tercero")
    @Convert(converter = BlobToStringConverter.class)
    private String respuestaTercero;
    @Column(name = "mensaje_error")
    private String mensajeError;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTercero() {
        return tercero;
    }

    public void setTercero(String tercero) {
        this.tercero = tercero;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getRespuestaTercero() {
        return respuestaTercero;
    }

    public void setRespuestaTercero(String respuestaTercero) {
        this.respuestaTercero = respuestaTercero;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
