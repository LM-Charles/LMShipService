package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.AddressEntity;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Created by rabiddesire on 23/08/15.
 */
public interface UserStorage {
    List<AppUserEntity> listAll(int pageSize, int offset);

    AppUserEntity get(Integer userId) throws ResourceNotFoundException;

    String create(AppUserEntity entity);

    void update(AppUserEntity entity);


    AppUserEntity getByEmail(String email) throws ResourceNotFoundException;

    String saveAddress(AddressEntity address);
}
