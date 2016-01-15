package com.longmendelivery.service.security;

<<<<<<< HEAD
import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.user.AppUserGroupType;
import org.springframework.beans.factory.annotation.Autowired;

=======
>>>>>>> origin/master
/**
 * Created by  rabiddesireon 04/06/15.
 */
public class TokenSecurity {
<<<<<<< HEAD

    @Autowired
    private UserStorage userStorage;

=======
>>>>>>> origin/master
    private static TokenSecurity instance;

    private TokenSecurity() {

    }

    public static TokenSecurity getInstance() {
        if (instance == null) {
            instance = new TokenSecurity();
        }
        return instance;
    }

<<<<<<< HEAD
    public void authorize(String token, SecurityPower requestedPower, Integer userId) throws NotAuthorizedException {
        if (token == null || userId == null) {
            throw new NotAuthorizedException("Must have a token and userId to validate this request");
        } else if (requestedPower.equals(SecurityPower.PUBLIC_READ)) {
            return;
        } else {
            try {
                boolean found = false;
                AppUserEntity user = userStorage.get(userId);

                if (!user.getApiToken().equals(token)) {
                    throw new NotAuthorizedException("token or user not logged in properly");
                }

                if (requestedPower.equals(SecurityPower.PRIVATE_READ) || requestedPower.equals(SecurityPower.PRIVATE_WRITE)) {
                    return;
                } else {
                    if (user.getUserGroup().equals(AppUserGroupType.ADMIN_USER)) {
                        return;
                    } else {
                        throw new NotAuthorizedException("token or user require admin power for this request");
                    }
                }
            } catch (ResourceNotFoundException e) {
                throw new NotAuthorizedException("Must have a valid user for this request");
            }
        }
=======

    public void authorize(String token, SecurityPower requestedPower) {

    }

    public void authorize(String token, SecurityPower requestedPower, Integer userId) throws NotAuthorizedException {
//        if (token == null || userId == null) {
//            throw new NotAuthorizedException("Must have a token and user to validate this request");
//        }

    }

    public String issueToken(String username, String password) {
        return "master";
    }

    public void invalidateToken(Integer userId) {
>>>>>>> origin/master
    }
}
