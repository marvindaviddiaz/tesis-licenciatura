package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.dao.IdentificadorDao;
import com.github.marvindaviddiaz.dto.IdentificadorDTO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class IdentificadorService {

    @Inject
    private IdentificadorDao dao;

    public List<IdentificadorDTO> obtenerIdentificadores(Integer servicioId) {
        return dao.obtenerIdentificadores(servicioId);
    }
}
