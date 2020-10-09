package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Favorito;
import com.github.marvindaviddiaz.dao.FactoryEntityManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoritoService extends FactoryEntityManager implements Serializable {

    public List<Favorito> buscarServicio(Integer usuario) {
        if (entityManager == null) {
            return new ArrayList<>();
        }
        return entityManager.createQuery("select f " +
                "from Favorito f " +
                "where f.usuario.codigo = :usuario", Favorito.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}
