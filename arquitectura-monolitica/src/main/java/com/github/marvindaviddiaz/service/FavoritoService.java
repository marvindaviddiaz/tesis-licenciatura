package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.*;
import com.github.marvindaviddiaz.dao.FavoritoDao;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FavoritoService implements Serializable {

    @Inject
    private FavoritoDao dao;

    public void guardarFavorito(String alias, Integer usuario, Servicio servicio, List<IdentificadorDTO> identificadores) {
        Favorito favorito = new Favorito();
        favorito.setAlias(alias);
        favorito.setServicio(servicio);
        favorito.setUsuario(new Usuario());
        favorito.getUsuario().setCodigo(usuario);

        List<IdentificadorFavorito> detalle = new ArrayList<>();
        identificadores.forEach(e -> {
            IdentificadorFavorito ifa = new IdentificadorFavorito();
            ifa.setId(new IdentificadorFavoritoId());
            ifa.getId().setFavorito(favorito);
            ifa.getId().setIdentificador(new Identificador());
            ifa.getId().getIdentificador().setId(e.getId());
            ifa.setValor(e.getValor());
            detalle.add(ifa);
        });

        dao.guardarFavorito(favorito, detalle);
    }

    public List<Favorito> obtenerFavoritos(Integer usuario) {
        return dao.obtenerFavoritos(usuario);
    }
}
