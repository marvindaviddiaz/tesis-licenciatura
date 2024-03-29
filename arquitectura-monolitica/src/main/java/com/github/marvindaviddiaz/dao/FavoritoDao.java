package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Favorito;
import com.github.marvindaviddiaz.bo.IdentificadorFavorito;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
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

    public void guardarFavorito(Favorito favorito, List<IdentificadorFavorito> detalle) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(favorito);
            entityManager.flush();
            detalle.forEach(entityManager::persist);
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public Favorito obtenerFavorito(Integer usuario, Integer favorito) {
        return entityManager.createQuery("select f " +
                "from Favorito f " +
                "where f.id = :id and f.usuario.codigo = :usuario", Favorito.class)
                .setParameter("id", favorito)
                .setParameter("usuario", usuario)
                .getSingleResult();
    }

    public List<IdentificadorFavorito> obtenerIdentificadoresFavorito(Integer usuario, Integer favorito) {
        return entityManager.createQuery("select f " +
                "from IdentificadorFavorito f " +
                "where f.id.favorito.id = :id and f.id.favorito.usuario.codigo = :usuario", IdentificadorFavorito.class)
                .setParameter("id", favorito)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}
