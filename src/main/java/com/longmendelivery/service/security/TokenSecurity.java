package com.longmendelivery.service.security;

import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.user.AppUserGroupType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class TokenSecurity {
    @Autowired
    private UserStorage userStorage;

    private static TokenSecurity instance;

    private TokenSecurity() {

    }

    public static TokenSecurity getInstance() {
        if (instance == null) {
            instance = new TokenSecurity();
        }
        return instance;
    }

    public void authorize(SecurityPower requestedPower, Integer requestedUserId, String authToken, Integer authUserId) throws NotAuthorizedException {
        if (authToken == null || authUserId == null) {
            throw new NotAuthorizedException("Must have a token and requestedUserId to validate this request");
        } else if (requestedPower.equals(SecurityPower.PUBLIC_READ)) {
            return;
        } else {
            try {
                AppUserEntity authUser = userStorage.get(authUserId);

                if (!authUser.getApiToken().equals(authToken)) {
                    throw new NotAuthorizedException("token or user not logged in properly");
                }

                if (requestedPower.equals(SecurityPower.PRIVATE_READ) || requestedPower.equals(SecurityPower.PRIVATE_WRITE)) {
                    if (!authUserId.equals(requestedUserId) && !authUser.getUserGroup().equals(AppUserGroupType.ADMIN_USER)) {
                        throw new NotAuthorizedException("token or user require admin power for this request");
                    }

                    return;
                } else {
                    if (!authUser.getUserGroup().equals(AppUserGroupType.ADMIN_USER)) {
                        throw new NotAuthorizedException("token or user require admin power for this request");
                    }
                    return;
                }
            } catch (ResourceNotFoundException e) {
                throw new NotAuthorizedException("Must have a valid user for this request");
            }
        }
    }
}
