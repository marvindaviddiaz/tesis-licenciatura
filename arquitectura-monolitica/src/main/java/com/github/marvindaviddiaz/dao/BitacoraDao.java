package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Bitacora;
import com.github.marvindaviddiaz.bo.BitacoraIdentificador;
import com.github.marvindaviddiaz.dto.Pagina;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class BitacoraDao extends FactoryEntityManager implements Serializable {

    public void grabarBitacora(Bitacora bitacora, List<BitacoraIdentificador> detalles) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(bitacora);
            detalles.forEach(entityManager::persist);
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public Pagina<Bitacora> buscar(Integer usuario, Date inicio, Date fin, String busqueda, Integer page, Integer maxResults) {
        TypedQuery<Long> countQuery = getQuery(usuario, inicio, fin, busqueda, page, maxResults, true, Long.class);
        Long count = countQuery.getSingleResult();
        List<Bitacora> resultList =  new ArrayList<>();
        if (count > 0) {
            TypedQuery<Bitacora> query = getQuery(usuario, inicio, fin, busqueda, page, maxResults, false, Bitacora.class);
            resultList = query.getResultList();
        }
        return new Pagina<>(resultList, count, page, maxResults);
    }

    private <T> TypedQuery<T> getQuery(Integer usuario, Date inicio, Date fin, String busqueda, Integer page, Integer maxResults, boolean count, Class<T> resultClass) {

        String query = count ? " select count(b) " : "select b ";
        query += " from Bitacora b where b.fecha between :inicio and :fin and b.usuario = :usuario ";
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            query += " and (lower(b.tercero) like :busqueda or lower(b.servicio) like :busqueda) ";
        }
        if (!count) {
            query += " order by b.fecha desc ";
        }
        TypedQuery<T> tQuery = entityManager.createQuery(query, resultClass)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .setParameter("usuario", usuario);

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            tQuery = tQuery.setParameter("busqueda", "%" + busqueda.toLowerCase() + "%");
        }

        if (!count) {
            tQuery = tQuery
                    .setMaxResults(maxResults)
                    .setFirstResult((page - 1) * maxResults);
        }

        return tQuery;
    }
}

