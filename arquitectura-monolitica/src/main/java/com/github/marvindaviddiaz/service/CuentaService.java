package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Cuenta;
import com.github.marvindaviddiaz.dao.CuentaDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CuentaService {

    @Inject
    private CuentaDao dao;

    public List<Cuenta> obtenerCuentas(Integer usuario) {
        return dao.obtenerCuentas(usuario);
    }
}
