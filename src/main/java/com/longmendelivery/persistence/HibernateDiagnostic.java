package com.longmendelivery.persistence;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Map;

/**
 * Created by desmond on 04/06/15.
 */
class HibernateDiagnostic {
    private final SessionFactory ourSessionFactory;
    private final ServiceRegistry serviceRegistry;

    public HibernateDiagnostic(){
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public String diagnostic() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        final Session session = getSession();
        try {
            stringBuilder.append("querying all the managed entities...");
            stringBuilder.append("\n");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                stringBuilder.append("executing: " + query.getQueryString());
                stringBuilder.append("\n");
                for (Object o : query.list()) {
                    stringBuilder.append("  " + o);
                    stringBuilder.append("\n");
                }
            }

            return stringBuilder.toString();
        } finally {
            session.close();
        }
    }
}
