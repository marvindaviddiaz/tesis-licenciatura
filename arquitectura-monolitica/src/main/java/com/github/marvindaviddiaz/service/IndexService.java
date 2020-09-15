package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndexService extends FactoryEntityManager implements Serializable {

    public List<Servicio> getAll() {
        if (entityManager == null) {
            return new ArrayList<>();
        }
        return entityManager.createQuery("from Servicio").getResultList();
    }

}
