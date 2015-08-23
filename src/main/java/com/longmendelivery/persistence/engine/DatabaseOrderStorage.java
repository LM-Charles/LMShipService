package com.longmendelivery.persistence.engine;

import com.longmendelivery.persistence.OrderStorage;
import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.entity.ShipOrderEntity;
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
public class DatabaseOrderStorage implements OrderStorage {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ShipOrderEntity> listAll(int pageSize, int offset) {
        Criteria criteria = getCriteria();
        criteria.setMaxResults(pageSize);
        criteria.setFirstResult(offset);
        List<ShipOrderEntity> result = criteria.list();
        return result;
    }

    private Criteria getCriteria() {
        Session session = getSession();
        return session.createCriteria(ShipOrderEntity.class);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public ShipOrderEntity get(Integer userId) throws ResourceNotFoundException {
        Session session = getSession();
        ShipOrderEntity result = (ShipOrderEntity) session.get(ShipOrderEntity.class, userId);
        return result;
    }


    @Override
    @Transactional(readOnly = false)
    public String create(ShipOrderEntity entity) {
        Session session = getSession();
        Integer result = (Integer) session.save(entity);
        return result.toString();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(ShipOrderEntity entity) {
        Session session = getSession();
        session.update(entity);
    }

    @Override
    public void createHistory(OrderStatusHistoryEntity orderStatusHistoryEntity) {
        Session session = getSession();
        session.save(orderStatusHistoryEntity);
    }


}
