package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.OrderEntity;
import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Created by  rabiddesireon 23/08/15.
 */
public interface OrderStorage {
    List<OrderEntity> listAll(int pageSize, int offset);

    OrderEntity get(Integer userId) throws ResourceNotFoundException;

    String create(OrderEntity entity);

    void update(OrderEntity entity);

    void createHistory(OrderStatusHistoryEntity orderStatusHistoryEntity);
}
