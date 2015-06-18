package com.longmendelivery.app;

import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.SecurityUtil;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.HibernateUtil;
import com.longmendelivery.persistence.model.AppUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
    @GET
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        ThrottleSecurity.getInstance().throttle(email);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUser user = (AppUser) writeSession.createCriteria(AppUser.class).add(Restrictions.eq("EMAIL", email)).uniqueResult();

            if (user == null) {
                return Response.status(Response.Status.FORBIDDEN).entity("Incorrect combination of email and password").build();
            } else if (user.getPassword_md5().equals(SecurityUtil.md5(password))) {
                if (user.getApiToken() == null) {
                    String token = SecurityUtil.generateSecureToken();
                    user.setApiToken(token);
                    writeSession.update(user);
                    tx.commit();
                }
                return Response.status(Response.Status.OK).entity(user.getId() + "@" + user.getApiToken()).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("Incorrect combination of email and password").build();
            }
        } finally {
            writeSession.close();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response logout(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUser user = (AppUser) writeSession.get(AppUser.class, userId);
            user.setApiToken(SecurityUtil.generateSecureToken());
            writeSession.update(user);
            tx.commit();
            return Response.status(Response.Status.OK).entity(user.getApiToken()).build();
        } finally {
            writeSession.close();
        }
    }
}