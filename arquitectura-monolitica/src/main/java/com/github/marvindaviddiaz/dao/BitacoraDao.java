package com.github.marvindaviddiaz.dao;

import com.github.marvindaviddiaz.bo.Bitacora;
import com.github.marvindaviddiaz.bo.BitacoraIdentificador;
import com.github.marvindaviddiaz.bo.Cuenta;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class BitacoraDao extends FactoryEntityManager implements Serializable {

    public void grabarBitacora(Bitacora bitacora, List<BitacoraIdentificador> detalles) {
        entityManager.persist(bitacora);
        detalles.forEach(entityManager::persist);
    }
}
