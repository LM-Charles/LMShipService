package com.longmendelivery.persistence;

import com.longmendelivery.persistence.entity.AppUserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by  rabiddesireon 23/08/15.
 */
public interface UserStorage {
    List<AppUserEntity> listAll(int pageSize, int offset);

    AppUserEntity get(Integer userId) throws ResourceNotFoundException;

    @Transactional(readOnly = false)
    String create(AppUserEntity entity);

    @Transactional(readOnly = false)
    void update(AppUserEntity entity);
}
