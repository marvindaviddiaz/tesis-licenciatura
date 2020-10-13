package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Favorito;
import com.github.marvindaviddiaz.dao.FavoritoDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.List;

@Singleton
public class FavoritoService implements Serializable {

    @Inject
    private FavoritoDao dao;

    public List<Favorito> obtenerFavoritos(Integer usuario) {
        return dao.obtenerFavoritos(usuario);
    }
}
