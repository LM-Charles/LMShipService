package com.longmendelivery.app;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.SecurityUtil;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.HibernateUtil;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.entity.AppUserGroupEntity;
import com.longmendelivery.persistence.entity.AppUserStatusEntity;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class AppUserResource {
    @SuppressWarnings("unchecked")
    @GET
    public Response listUsers(@QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<AppUserEntity> users = session.createCriteria(AppUserEntity.class).list();
        tx.commit();
        return Response.status(Response.Status.OK).entity(ReflectionToStringBuilder.toString(users)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("phone") String phone,
                             @FormParam("email") String email,
                             @FormParam("password") String password,
                             @FormParam("firstName") String firstName,
                             @FormParam("lastName") String lastName) {
        ThrottleSecurity.getInstance().throttle();
        String passwordMD5 = SecurityUtil.md5(password);
        AppUserGroupEntity userGroup = AppUserGroupEntity.APP_USER;
        AppUserStatusEntity status = AppUserStatusEntity.NEW;
        AppUserEntity newUser = new AppUserEntity(phone, email, passwordMD5, userGroup, status);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();

        Integer userId = (Integer) writeSession.save(newUser);
        tx.commit();
        return Response.status(Response.Status.OK).entity(userId).build();
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        AppUserEntity user = (AppUserEntity) session.get(AppUserEntity.class, userId);
        user.setApiToken("hidden");
        user.setPassword_md5("hidden");
        user.setVerificationString("hidden");
        tx.rollback();
        return Response.status(Response.Status.OK).entity(ReflectionToStringBuilder.toString(user)).build();
    }

    @GET
    @Path("/{userId}/admin")
    public Response getUserAdmin(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN, userId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        AppUserEntity user = (AppUserEntity) session.get(AppUserEntity.class, userId);
        tx.commit();
        return Response.status(Response.Status.OK).entity(ReflectionToStringBuilder.toString(user)).build();
    }

    @POST
    @Path("/{userId}")
    public Response changeUserDetail(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{userId}/activation")
    public Response sendActivationVerification(@PathParam("userId") Integer userId, @QueryParam("token") String token) throws DependentServiceException {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        String randomVerification = SecurityUtil.generateSecureVerificationCode();

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            user.setUserStatus(AppUserStatusEntity.PENDING_VERIFICATION_REGISTER);
            user.setVerificationString(randomVerification);
            writeSession.update(user);
            tx.commit();

            String smsBody = buildRegistrationVerificationMessage(user.getFirstName(), user.getLastName(), randomVerification);
            new TwilioSMSClient().sendSMS(user.getPhone(), smsBody);
            return Response.status(Response.Status.OK).entity("Register verification sent for " + user.getEmail() + " to " + user.getPhone()).build();

        } finally {
            writeSession.close();
        }
    }

    @POST
    @Path("/{userId}/activation/{verificationCode}")
    public Response activate(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);

            if (!user.getUserStatus().equals(AppUserStatusEntity.PENDING_VERIFICATION_REGISTER)) {
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("User is not pending verification").build();
            } else if (user.getVerificationString().equals(verificationCode)) {
                user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
                user.setUserStatus(AppUserStatusEntity.ACTIVE);
                writeSession.update(user);
                tx.commit();
                return Response.status(Response.Status.OK).entity("User activated").build();
            } else {
                return Response.status(Response.Status.FORBIDDEN.getStatusCode()).entity("Verification code not accepted").build();
            }
        } finally {
            writeSession.close();
        }
    }

    private String buildRegistrationVerificationMessage(String firstName, String lastName, String randomVerification) {
        return new StringBuilder().append("Dear ").append(firstName).append(" ").append(lastName).append(" ,").append("your verification code is: ").append(randomVerification).toString();
    }

    @POST
    @Path("/{userId}/resetPassword")
    public Response sendChangePasswordVerification(@PathParam("userId") Integer userId) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(userId);

        String randomVerification = SecurityUtil.generateSecureVerificationCode();
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            if (user.getUserStatus().equals(AppUserStatusEntity.DISABLED)) {
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("Cannot request password change of disabled user").build();
            } else {
                user.setVerificationString(randomVerification);
                writeSession.update(user);
                tx.commit();

                String smsBody = buildRegistrationVerificationMessage(user.getFirstName(), user.getLastName(), randomVerification);
                new TwilioSMSClient().sendSMS(user.getPhone(), smsBody);
                return Response.status(Response.Status.OK).entity("Password verification sent for " + user.getEmail() + " to " + user.getPhone()).build();
            }
        } finally {
            writeSession.close();
        }
    }


    @POST
    @Path("/{userId}/resetPassword/{verificationCode}")
    public Response changePassword(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, String password) {
        ThrottleSecurity.getInstance().throttle(userId);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);

            if (user.getUserStatus().equals(AppUserStatusEntity.DISABLED)) {
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("Cannot request password change of disabled user").build();
            } else if (user.getVerificationString().equals(verificationCode)) {
                user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
                user.setPassword_md5(SecurityUtil.md5(password));
                user.setApiToken(SecurityUtil.generateSecureToken());
                writeSession.update(user);
                tx.commit();
                return Response.status(Response.Status.OK).entity("Password changed, token refreshed").build();
            } else {
                return Response.status(Response.Status.FORBIDDEN.getStatusCode()).entity("Verification code not accepted").build();
            }
        } finally {
            writeSession.close();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response disableUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);

            user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
            user.setPassword_md5(SecurityUtil.md5(SecurityUtil.generateSecureToken()));
            user.setApiToken(SecurityUtil.generateSecureToken());
            user.setUserStatus(AppUserStatusEntity.DISABLED);
            writeSession.update(user);
            tx.commit();
            return Response.status(Response.Status.OK).entity("Password changed, token refreshed").build();
        } finally {
            writeSession.close();
        }
    }
}