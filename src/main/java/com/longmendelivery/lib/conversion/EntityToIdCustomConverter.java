package com.longmendelivery.lib.conversion;

import com.longmendelivery.persistence.DAOEntity;
import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by desmond on 21/06/15.
 */
@Component
public class EntityToIdCustomConverter implements CustomConverter {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Object convert(Object dest, Object src, Class<?> destClass, Class<?> srcClass) {
        if (src == null) {
            return null;
        }

        if (src instanceof DAOEntity) {
            return ((DAOEntity) src).getId();
        } else if (src instanceof Integer) {
            Session session = sessionFactory.getCurrentSession();
            try {
                return session.get(destClass, (Serializable) src);
            } catch (Exception e) {
                throw new MappingException(e);
            } finally {
                session.close();
            }
        } else {
            throw new MappingException("EntityToIdCustomConverter " + "used incorrectly. Arguments passed in were:" + dest + " and " + src);
        }
    }
}
