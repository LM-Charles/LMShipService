package com.longmendelivery.service;

import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.engine.DatabaseUserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.response.LoginResponseModel;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.SecurityUtil;
import com.longmendelivery.service.security.ThrottleSecurity;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces("application/json")
public class LoginResource {
    UserStorage userStorage = DatabaseUserStorage.getInstance();

    @POST
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        ThrottleSecurity.getInstance().throttle(email);


        AppUserEntity user = null;
        try {
            user = userStorage.getByEmail(email);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
        }

        if (user.getPassword_md5().equals(SecurityUtil.md5(password))) {
            if (user.getApiToken() == null) {
                String token = SecurityUtil.generateSecureToken();
                user.setApiToken(token);
                userStorage.update(user);
            }
            return Response.status(Response.Status.OK).entity(new LoginResponseModel(user.getId(), user.getApiToken())).build();
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