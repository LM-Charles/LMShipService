package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Created by rabiddesire on 23/08/15.
 * ok
 */
public interface ShipmentStorage {
    List<ShipmentEntity> listAll(int pageSize, int offset);

    ShipmentEntity get(Integer userId) throws ResourceNotFoundException;

    String create(ShipmentEntity entity);

    void update(ShipmentEntity entity);
}
