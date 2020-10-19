package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Cuenta;
import com.github.marvindaviddiaz.bo.Favorito;
import com.github.marvindaviddiaz.bo.IdentificadorFavorito;
import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.dto.ConsultaPagoDTO;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;
import com.github.marvindaviddiaz.service.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ViewScoped
@Named("consultaPagoBean")
public class ConsultaPagoBean implements Serializable {

    @Inject
    private transient ServicioService servicioService;
    @Inject
    private transient IdentificadorService identificadorService;
    @Inject
    private transient TerceroService terceroService;
    @Inject
    private transient CuentaService cuentaService;
    @Inject
    private transient FavoritoService favoritoService;

    private Servicio servicio;
    private Favorito favorito;
    private Integer servicioId;
    private Integer favoritoId;
    private List<IdentificadorDTO> identificadores;
    private BigDecimal monto;
    private List<Cuenta> cuentas;
    private Integer numeroCuenta;
    private Cuenta cuenta;
    private String referenciaPago;

    private boolean paso1 = true;
    private boolean paso2 = false;
    private boolean paso3 = false;
    private boolean paso4 = false;
    private boolean paso5 = false;

    private Integer usuario = 36; // TODO

    @PostConstruct
    public void init() {
    }

    public void preRenderView() {
        if (servicioId != null && (servicio == null || !servicio.getId().equals(servicioId))) {
            servicio = servicioService.obtenerServicio(servicioId);
            identificadores = identificadorService.obtenerIdentificadores(servicioId);
        }

        if (favoritoId != null && (favorito == null || !favorito.getId().equals(favoritoId))) {
            this.favorito = favoritoService.obtenerFavorito(usuario, favoritoId);
            servicio = favorito.getServicio();
            identificadores = identificadorService.obtenerIdentificadores(servicio.getId());
            List<IdentificadorFavorito> identificadorFavoritos = favoritoService.obtenerIdentificadoresFavorito(usuario, favoritoId);
            for (IdentificadorFavorito idf : identificadorFavoritos) {
                for (IdentificadorDTO dto : identificadores) {
                    if (dto.getId().equals(idf.getId().getIdentificador().getId())) {
                        dto.setValor(idf.getValor());
                        break;
                    }
                }
            }
            this.consultar();
        }
    }


    public void consultar() {
        if (identificadores != null && identificadores.stream().anyMatch(i -> i.getValor() != null && !i.getValor().isEmpty())) {
            // Consulta
            ConsultaPagoDTO consultar = terceroService.consultar(servicio, identificadores);
            monto = consultar.getSaldo();
            // Obtener Cuentas
            cuentas = cuentaService.obtenerCuentas(usuario);
            paso2 = true;
            paso1 = false;
        }
    }

    public void confirmar() {
        if (paso2 && numeroCuenta != null) {
            this.cuenta = this.cuentas.stream().filter(f -> numeroCuenta.equals(f.getNumero())).findFirst().orElse(null);
            paso2 = false;
            paso3 = true;
        }
    }


    public void pagar() {
        if (paso3 && cuenta != null) {
            referenciaPago = terceroService.pagar(usuario, cuenta.getNumero(), servicio, identificadores, monto);
            paso3 = false;
            paso4 = true;
        }
    }

    public void guardarFavorito() {
        if (paso4) {
            paso4 = false;
            paso5 = true;
        }
    }

    public String confirmarGuardarFavorito(String alias) {
        if (alias != null) {
            favoritoService.guardarFavorito(alias, usuario, servicio, identificadores);
        }
        return "index";
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    public List<IdentificadorDTO> getIdentificadores() {
        return identificadores;
    }

    public void setIdentificadores(List<IdentificadorDTO> identificadores) {
        this.identificadores = identificadores;
    }

    public boolean isPaso1() {
        return paso1;
    }

    public void setPaso1(boolean paso1) {
        this.paso1 = paso1;
    }

    public boolean isPaso2() {
        return paso2;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setPaso2(boolean paso2) {
        this.paso2 = paso2;
    }

    public boolean isPaso3() {
        return paso3;
    }

    public void setPaso3(boolean paso3) {
        this.paso3 = paso3;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Integer numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public boolean isPaso4() {
        return paso4;
    }

    public void setPaso4(boolean paso4) {
        this.paso4 = paso4;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public boolean isPaso5() {
        return paso5;
    }

    public void setPaso5(boolean paso5) {
        this.paso5 = paso5;
    }

    public Integer getFavoritoId() {
        return favoritoId;
    }

    public void setFavoritoId(Integer favoritoId) {
        this.favoritoId = favoritoId;
    }

    public Favorito getFavorito() {
        return favorito;
    }

    public void setFavorito(Favorito favorito) {
        this.favorito = favorito;
    }
}
