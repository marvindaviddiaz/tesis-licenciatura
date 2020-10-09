package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Identificador;
import com.github.marvindaviddiaz.dao.FactoryEntityManager;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IdentificadorService extends FactoryEntityManager implements Serializable {

    public List<IdentificadorDTO> obtenerIdentificadores(Integer servicio) {
        if (entityManager == null) {
            return new ArrayList<>();
        }
        return entityManager.createQuery("select i " +
                "from Identificador i " +
                "where i.servicio.id = :servicio", Identificador.class)
                .setParameter("servicio", servicio)
                .getResultList()
                .stream()
                .map(IdentificadorDTO::new)
                .collect(Collectors.toList());

    }
}
