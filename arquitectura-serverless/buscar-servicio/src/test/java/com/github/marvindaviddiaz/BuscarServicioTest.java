package com.github.marvindaviddiaz;

import com.github.marvindaviddiaz.dto.ServicioDTO;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.util.List;

public class BuscarServicioTest {

    @Test
    public void buscarServicioTest() {
        ServicioService servicio = new ServicioService();
        List<ServicioDTO> servicios = servicio.handleRequest("tel", null);
        servicios.forEach(e -> JAXB.marshal(e, System.out));
    }

}
