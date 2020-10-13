package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Cuenta;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CuentaDao extends FactoryEntityManager implements Serializable {

    public List<Cuenta> obtenerCuentas(Integer usuario) {
        if (entityManager == null) {
            return new ArrayList<>();
        }
        return entityManager.createQuery("select c " +
                "from Cuenta c " +
                "where c.usuario = :usuario", Cuenta.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}
