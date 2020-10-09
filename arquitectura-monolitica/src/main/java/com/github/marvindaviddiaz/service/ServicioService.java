package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Servicio;
import com.github.marvindaviddiaz.dao.FactoryEntityManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServicioService extends FactoryEntityManager implements Serializable {

    public List<Servicio> buscarServicio(String busqueda) {
        if (entityManager == null) {
            return new ArrayList<>();
        }
        return entityManager.createQuery("select s " +
                "from Servicio s inner join Tercero t on s.tercero = t " +
                "inner join PalabraClave pc on t = pc.tercero " +
                "inner join TerceroCategoria tc on t = tc.terceroCategoriaId.tercero " +
                "inner join Categoria c on tc.terceroCategoriaId.categoria = c " +
                "where lower(s.nombre) like :busqueda or lower(t.nombre) like :busqueda or lower(pc.palabraClave) like :busqueda or lower(c.nombre) like :busqueda " +
                "group by s.id, s.nombre, t.id, t.nombre", Servicio.class)
                .setParameter("busqueda", "%" + busqueda + "%")
                .getResultList();
    }

    public Servicio obtenerServicio(Integer id) {
        if (entityManager == null) {
            return null;
        }
        return entityManager.find(Servicio.class, id);
    }
}
