package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.entity.ShipOrderEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Created by  rabiddesireon 23/08/15.
 */
public interface OrderStorage {
    List<ShipOrderEntity> listAll(int pageSize, int offset);

    ShipOrderEntity get(Integer userId) throws ResourceNotFoundException;

    String create(ShipOrderEntity entity);

    void update(ShipOrderEntity entity);

    void createHistory(OrderStatusHistoryEntity orderStatusHistoryEntity);

    Integer recursiveCreate(ShipOrderEntity shipOrderEntity);
}
