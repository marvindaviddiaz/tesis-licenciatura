package com.github.marvindaviddiaz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.marvindaviddiaz.modelo.Servicio;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscarServicio implements RequestHandler<String, List<Servicio>> {

    private static final Logger logger = Logger.getLogger(BuscarServicio.class.getName());

    private static SessionFactory sessionFactory;

    @Override
    public List<Servicio> handleRequest(String busqueda, Context context) {
        logger.log(Level.INFO, "{0} Petición: {1}, Contexto: {2}", new Object[]{context.getAwsRequestId(), busqueda, context});

        EntityManager manager = sessionFactory.createEntityManager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Servicio> getAll = builder.createQuery(Servicio.class);
        Root<Servicio> root = getAll.from(Servicio.class);

        getAll
                .select(root)
                .orderBy(builder.asc(root.get("id")));

        List<Servicio> services = manager.createQuery(getAll).getResultList();
        manager.close();

        return services;
    }


    static {
        Configuration configuration = new Configuration();

        String jdbcUrl = String.format(
                "jdbc:mysql://%s/%s",
                System.getenv("RDS_ENDPOINT"),
                System.getenv("RDS_DB_NAME"));

        configuration
                .addAnnotatedClass(Servicio.class)
                .setProperty("hibernate.connection.url", jdbcUrl)
                .setProperty("hibernate.connection.username", System.getenv("RDS_USERNAME"))
                .setProperty("hibernate.connection.password", System.getenv("RDS_PASSWORD"))
                .configure();

        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
