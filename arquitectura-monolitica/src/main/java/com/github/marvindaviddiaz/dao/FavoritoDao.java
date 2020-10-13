package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Favorito;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FavoritoDao extends FactoryEntityManager implements Serializable {

    public List<Favorito> obtenerFavoritos(Integer usuario) {
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
