package com.longmendelivery.lib.conversion;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.persistence.util.HibernateUtil;
import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * Created by  rabiddesireon 21/06/15.
 */
public class EntityToIdCustomConverter implements CustomConverter {
    @Override
    public Object convert(Object dest, Object src, Class<?> destClass, Class<?> srcClass) {
        if (src == null) {
            return null;
        }

        if (src instanceof DAOEntity) {
            return ((DAOEntity) src).getId();
        } else if (src instanceof Integer) {
            Session session = HibernateUtil.getSessionFactory().openSession();
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
