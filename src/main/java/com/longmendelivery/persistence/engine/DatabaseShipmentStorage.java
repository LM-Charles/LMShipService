package com.longmendelivery.persistence.engine;

import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desmond on 23/08/15.
 */
@Component
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = true)
public class DatabaseShipmentStorage implements ShipmentStorage {
    static ShipmentStorage instance;

    public static ShipmentStorage getInstance() {
        if (instance == null) {
            instance = new DatabaseShipmentStorage();
        }
        return instance;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ShipmentEntity> listAll(int pageSize, int offset) {
        Criteria criteria = getCriteria();
        criteria.setMaxResults(pageSize);
        criteria.setFirstResult(offset);
        List<ShipmentEntity> result = criteria.list();
        return result;
    }

    private Criteria getCriteria() {
        Session session = getSession();
        return session.createCriteria(ShipmentEntity.class);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public ShipmentEntity get(Integer userId) throws ResourceNotFoundException {
        Session session = getSession();
        ShipmentEntity result = (ShipmentEntity) session.get(ShipmentEntity.class, userId);
        return result;
    }


    @Override
    @Transactional(readOnly = false)
    public String create(ShipmentEntity entity) {
        Session session = getSession();
        Integer result = (Integer) session.save(entity);
        return result.toString();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(ShipmentEntity entity) {
        Session session = getSession();
        session.update(entity);
    }
}
