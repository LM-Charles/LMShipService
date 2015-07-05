package com.longmendelivery.service;

import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.SecurityUtil;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.util.HibernateUtil;
import com.longmendelivery.service.model.response.LoginResponseModel;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces("application/json")
public class LoginResource {
    @POST
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        ThrottleSecurity.getInstance().throttle(email);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.createCriteria(AppUserEntity.class).add(Restrictions.eq("email", email)).uniqueResult();

            if (user == null) {
                return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
            } else if (user.getPassword_md5().equals(SecurityUtil.md5(password))) {
                if (user.getApiToken() == null) {
                    String token = SecurityUtil.generateSecureToken();
                    user.setApiToken(token);
                    writeSession.update(user);
                    tx.commit();
                }
                return Response.status(Response.Status.OK).entity(new LoginResponseModel(user.getId(), user.getApiToken())).build();
            } else {
                return ResourceResponseUtil.generateForbiddenMessage("Incorrect combination of email and password");
            }
        } finally {
            writeSession.close();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response logout(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (com.longmendelivery.lib.security.NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            user.setApiToken(SecurityUtil.generateSecureToken());
            writeSession.update(user);
            tx.commit();
            return ResourceResponseUtil.generateOKMessage("successfully logged out");
        } finally {
            writeSession.close();
        }
    }
}