package com.github.marvindaviddiaz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marvindaviddiaz.bo.Interfaz;
import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.dao.FactoryEntityManager;
import com.github.marvindaviddiaz.dao.InterfazDao;
import com.github.marvindaviddiaz.dto.ConsultaPagoDTO;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;
import org.apache.commons.text.StringSubstitutor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class TerceroService extends FactoryEntityManager implements Serializable {

    private static final Logger logger = Logger.getLogger(TerceroService.class.getName());

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private InterfazDao interfazDao;
    @Inject
    private BitacoraService bitacoraService;


    public ConsultaPagoDTO consultar(Servicio servicio, List<IdentificadorDTO> identificadores) {
        Interfaz interfaz = interfazDao.obtenerInterfaz(servicio.getId(), "C");

        Map<String, String> params = new HashMap<>();
        for (IdentificadorDTO id : identificadores) {
            params.put(id.getCodigo(), id.getValor());
        }

        StringSubstitutor sub = new StringSubstitutor(params);
        String mensaje = sub.replace(interfaz.getMensaje());
        logger.log(Level.INFO, mensaje);

        if ("REST".equals(interfaz.getProtocolo())) {
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .method(interfaz.getMetodo(), HttpRequest.BodyPublishers.ofString(mensaje))
                        .uri(URI.create(interfaz.getUrl()))
                        .setHeader("Content-Type", "application/json")
                        .timeout(Duration.ofMillis(interfaz.getTimeout()))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                logger.log(Level.INFO, "Respuesta {0}, {1}", new Object[]{response.statusCode(), response.body()});
                return mapper.readValue(response.body(), ConsultaPagoDTO.class);
            } catch (Exception e) {
                // TODO: REINTENTOS
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return null;
    }

    public String pagar(Integer usuario, Integer cuenta, Servicio servicio, List<IdentificadorDTO> identificadores, BigDecimal monto) {
        Interfaz interfaz = interfazDao.obtenerInterfaz(servicio.getId(), "P");
        String idTrx = UUID.randomUUID().toString();
        Map<String, String> params = new HashMap<>();
        params.put("VALOR", monto.toString());
        for (IdentificadorDTO id : identificadores) {
            params.put(id.getCodigo(), id.getValor());
        }

        StringSubstitutor sub = new StringSubstitutor(params);
        String mensaje = sub.replace(interfaz.getMensaje());
        logger.log(Level.INFO, mensaje);

        String exception = null;
        String respuestaTercero = null;
        if ("REST".equals(interfaz.getProtocolo())) {
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .method(interfaz.getMetodo(), HttpRequest.BodyPublishers.ofString(mensaje))
                        .uri(URI.create(interfaz.getUrl()))
                        .setHeader("Content-Type", "application/json")
                        .timeout(Duration.ofMillis(interfaz.getTimeout()))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                logger.log(Level.INFO, "Respuesta {0}, {1}", new Object[]{response.statusCode(), response.body()});
                respuestaTercero = response.body();
            } catch (Exception e) { // TODO REINTENTOS
                exception = e.getMessage();
                logger.log(Level.SEVERE, e.getMessage(), e);
            } finally {
                bitacoraService.guardarBitacora(idTrx, usuario, servicio, cuenta, monto, respuestaTercero, exception, params);
            }
        }

        return idTrx;
    }
}
