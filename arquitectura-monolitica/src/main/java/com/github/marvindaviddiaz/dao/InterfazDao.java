package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Interfaz;

import java.io.Serializable;

public class InterfazDao extends FactoryEntityManager implements Serializable {

    public Interfaz obtenerInterfaz(Integer servicio, String tipoOperacion) {
        return entityManager.createQuery("select i " +
                "from Interfaz i where i.servicio.id = :servicio and i.tipoOperacion = :tipoOperacion", Interfaz.class)
                .setParameter("servicio", servicio)
                .setParameter("tipoOperacion", tipoOperacion)
                .getSingleResult();
    }
}
