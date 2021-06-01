package com.github.marvindaviddiaz.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

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
            Map<String, String> env = System.getenv();
            Map<String, Object> configOverrides = new HashMap<>();
            if (env.containsKey("RDS_ENDPOINT")) {
                configOverrides.put("javax.persistence.jdbc.url", String.format("jdbc:mysql://%s/%s",
                        env.get("RDS_ENDPOINT"), env.get("RDS_DB_NAME")));
            }
            if (env.containsKey("RDS_USERNAME")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get("RDS_USERNAME"));
            }
            if (env.containsKey("RDS_PASSWORD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get("RDS_PASSWORD"));
            }
            // You can put more code in here to populate configOverrides...
            return entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME, configOverrides);
        }
        return entityManagerFactory;
    }

    public void closeConnection(){
        if(entityManager.isOpen() && entityManager != null){
            entityManager.close();
        }
    }

}
