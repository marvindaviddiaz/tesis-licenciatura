package com.github.marvindaviddiaz.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryEntityManager {

    private static final String PERSISTENCE_NAME = "TesisPersistence";
    protected EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;

    public FactoryEntityManager() {
        entityManager = getEntityManager();
    }

    public EntityManager getEntityManager(){
        if(entityManager != null && entityManager.isOpen()){
            return entityManager;
        }
        return entityManager = getEntityManagerFactory().createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory(){
        if(entityManagerFactory == null || ! entityManagerFactory.isOpen()){
            return entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        }
        return entityManagerFactory;
    }

    public void closeConnection(){
        if(entityManager.isOpen() && entityManager != null){
            entityManager.close();
        }
    }

}
