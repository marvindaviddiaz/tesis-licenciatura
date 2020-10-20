package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.*;
import com.github.marvindaviddiaz.dao.BitacoraDao;
import com.github.marvindaviddiaz.dto.Pagina;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Singleton
public class BitacoraService implements Serializable {

    @Inject
    private BitacoraDao dao;

    public void guardarBitacora(String idTrx, Integer usuario, Servicio servicio, Integer cuenta, BigDecimal monto,
                                        String respuestaTercero, String error, Map<String, String> identificadores) {
        Bitacora bitacora = new Bitacora();
        bitacora.setId(idTrx);
        bitacora.setFecha(new Date());
        bitacora.setUsuario(usuario);
        bitacora.setEstado("P");
        bitacora.setTipo("P");
        bitacora.setTercero(servicio.getTercero().getNombre());
        bitacora.setServicio(servicio.getNombre());
        bitacora.setCuenta(cuenta);
        bitacora.setMonto(monto);
        bitacora.setRespuestaTercero(respuestaTercero);
        bitacora.setMensajeError(error);

        List<BitacoraIdentificador> detalle = new ArrayList<>();
        identificadores.keySet().forEach(key -> {
            BitacoraIdentificador bi = new BitacoraIdentificador();
            bi.setId(new BitacoraIdentificadorId());
            bi.getId().setBitacora(bitacora.getId());
            bi.getId().setIdentificador(key);
            bi.setValor(identificadores.get(key));
            detalle.add(bi);
        });

        dao.grabarBitacora(bitacora, detalle);
    }

    public Pagina<Bitacora> buscar(Integer usuario, Date inicio, Date fin, String busqueda, Integer page, Integer maxResults) {
        return dao.buscar(usuario, inicio, fin, busqueda, page, maxResults);
    }
}
