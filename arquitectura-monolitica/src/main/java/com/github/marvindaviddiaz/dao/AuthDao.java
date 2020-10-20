package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.dto.UsuarioDTO;

import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public class AuthDao extends FactoryEntityManager implements Serializable {

    public UsuarioDTO getUser(String username) {
        return entityManager.createQuery("select new com.github.marvindaviddiaz.dto.UsuarioDTO(u.nombreCompleto, c.password) from Credencial c inner  join Usuario u on c.codigo = u.codigo" +
                        " where c.codigo = :codigo and c.habilitado = true", UsuarioDTO.class)
                .setParameter("codigo", Integer.valueOf(username))
                .getSingleResult();
    }
}

