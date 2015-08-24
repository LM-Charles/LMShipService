package com.longmendelivery.service;

import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.user.LoginResponse;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.SecurityUtil;
import com.longmendelivery.service.security.ThrottleSecurity;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("/login")
@Produces("application/json")
@Component
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = true)
public class LoginResource {
    @Autowired
    private UserStorage userStorage;
    @POST
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        ThrottleSecurity.getInstance().throttle(email);


        AppUserEntity user = null;
        try {
            user = userStorage.getByEmail(email);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
        }

        if (Arrays.equals(user.getPassword_md5(), SecurityUtil.md5(password))) {
            if (user.getApiToken() == null) {
                String token = SecurityUtil.generateSecureToken();
                user.setApiToken(token);
                userStorage.update(user);
            }
            return Response.status(Response.Status.OK).entity(new LoginResponse(user.getId(), user.getApiToken())).build();
        } else {
            return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response logout(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        AppUserEntity user = null;
        try {
            user = userStorage.get(userId);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
        }
        user.setApiToken(SecurityUtil.generateSecureToken());
        userStorage.update(user);
        return ResourceResponseUtil.generateOKMessage("successfully logged out");
    }

}