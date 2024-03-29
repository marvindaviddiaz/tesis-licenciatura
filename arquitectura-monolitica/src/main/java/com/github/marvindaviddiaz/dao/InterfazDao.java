package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Interfaz;

import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public class InterfazDao extends FactoryEntityManager implements Serializable {

    public Interfaz obtenerInterfaz(Integer servicio, String tipoOperacion) {
        return entityManager.createQuery("select i " +
                "from Interfaz i where i.servicio.id = :servicio and i.tipoOperacion = :tipoOperacion", Interfaz.class)
                .setParameter("servicio", servicio)
                .setParameter("tipoOperacion", tipoOperacion)
                .getSingleResult();
    }
}
