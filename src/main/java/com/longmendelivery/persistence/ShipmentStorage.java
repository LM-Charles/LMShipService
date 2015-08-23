package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Created by desmond on 23/08/15.
 */
public interface ShipmentStorage {
    List<ShipmentEntity> listAll(int pageSize, int offset);

    ShipmentEntity get(Integer userId) throws ResourceNotFoundException;

    String create(ShipmentEntity entity);

    void update(ShipmentEntity entity);
}
