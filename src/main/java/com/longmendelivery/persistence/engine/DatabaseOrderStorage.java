package com.longmendelivery.persistence.engine;

import com.longmendelivery.persistence.OrderStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.entity.ShipOrderEntity;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by  rabiddesireon 23/08/15.
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
        if (result == null) {
            throw new ResourceNotFoundException();
        }
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

    @Override
    public Integer recursiveCreate(ShipOrderEntity shipOrderEntity) {
        Session session = getSession();
        for (ShipmentEntity shipment : shipOrderEntity.getShipments()) {
            session.save(shipment);
        }
        session.save(shipOrderEntity.getFromAddress());
        session.save(shipOrderEntity.getToAddress());
        for (OrderStatusHistoryEntity history : shipOrderEntity.getOrderStatuses()) {
            session.save(history);
        }
        Integer result = (Integer) session.save(shipOrderEntity);
        return result;
    }

    @Override
    public List<ShipOrderEntity> getOrderForUser(AppUserEntity user, Integer limit, Integer offset) {
        Session session = getSession();
        Query query = session.createQuery("from ShipOrder shipOrder where shipOrder.client = :user order by shipOrder.orderDate, shipOrder.id desc");
        query.setParameter("user", user);
        query.setMaxResults(calculateLimit(limit));
        query.setFirstResult(calculateOffset(offset));
        return query.list();
    }

    @Override
    public List<OrderStatusHistoryEntity> getOrderStatusHistory(ShipOrderEntity order) {
        Session session = getSession();
        Query query = session.createQuery("from OrderStatusHistory history where history.order = :order order by history.id desc");
        query.setParameter("order", order);
        return query.list();
    }

    private Integer calculateOffset(Integer offset) {
        return (offset == null) ? 0 : offset;
    }

    private int calculateLimit(Integer limit) {
        return Math.min(limit, 32);
    }
}
