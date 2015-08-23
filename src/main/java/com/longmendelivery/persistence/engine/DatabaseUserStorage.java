package com.longmendelivery.persistence.engine;

import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
public class DatabaseUserStorage implements UserStorage {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<AppUserEntity> listAll(int pageSize, int offset) {
        Criteria criteria = getCriteria();
        criteria.setMaxResults(pageSize);
        criteria.setFirstResult(offset);
        List<AppUserEntity> result = criteria.list();
        return result;
    }

    private Criteria getCriteria() {
        Session session = getSession();
        return session.createCriteria(AppUserEntity.class);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public AppUserEntity get(Integer userId) throws ResourceNotFoundException {
        Session session = getSession();
        AppUserEntity result = (AppUserEntity) session.get(AppUserEntity.class, userId);
        return result;
    }


    @Override
    @Transactional(readOnly = false)
    public String create(AppUserEntity entity) {
        Session session = getSession();
        Integer result = (Integer) session.save(entity);
        return result.toString();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(AppUserEntity entity) {
        Session session = getSession();
        session.update(entity);
    }

    @Override
    public AppUserEntity getByEmail(String email) throws ResourceNotFoundException {
        Criteria criteria = getCriteria();
        return (AppUserEntity) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }
}
