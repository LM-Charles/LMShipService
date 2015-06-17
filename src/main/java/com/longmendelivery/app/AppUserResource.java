package com.longmendelivery.app;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.HibernateUtil;
import com.longmendelivery.persistence.model.AppUser;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Path("/user")
public class AppUserResource {
    @SuppressWarnings("unchecked")
    @GET
    public Response listUsers(@QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<AppUser> users = session.createCriteria(AppUser.class).list();
        tx.commit();
        return Response.status(200).entity(ReflectionToStringBuilder.toString(users)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(     @FormParam("phone") String phone,
                                  @FormParam("email") String email,
                                  @FormParam("password") String password,
                                  @FormParam("firstName") String firstName,
                                  @FormParam("lastName") String lastName) {
        ThrottleSecurity.getInstance().throttle();
        String passwordMD5 = this.md5(password);
        String userGroup = "APP_USER";
        String status = "UNVERIFIED";
        AppUser newUser = new AppUser(phone, email,  passwordMD5, userGroup, status);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();

            Integer userId = (Integer) writeSession.save(newUser);
            tx.commit();
            return Response.status(200).entity(userId).build();
    }

    private String md5(String password) {
        try {
            byte[] bytes = password.getBytes("UTF-8");
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytes);
            return digest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        AppUser user = (AppUser) session.get(AppUser.class, userId);
        tx.commit();
        return Response.status(200).entity(ReflectionToStringBuilder.toString(user)).build();
    }

    @POST
    @Path("/{userId}")
    public Response changeUserDetail(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}/activation")
    public Response sendActivationVerification(@PathParam("userId") Integer userId, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}/activation/{verificationCode}")
    public Response activate(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("token") String token) throws DependentServiceException {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        SecureRandom randomGenerator = new SecureRandom();
        String randomVerification = new BigInteger(130, randomGenerator).toString(32);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUser user = (AppUser) writeSession.get(AppUser.class, userId);
            user.setUserStatus("PENDING_VERIFY_REGISTER");
            user.setVerificationString(randomVerification);
            writeSession.update(user);
            tx.commit();

            String smsBody = buildRegistrationVerificationMessage(user.getFirstName(), user.getLastName(), randomVerification);
            new TwilioSMSClient().sendSMS(user.getPhone(), smsBody);
            return Response.status(200).entity("Verification sent for " + user.getEmail() + " to " + user.getPhone()).build();

        } finally {
            writeSession.close();
        }

    }

    private String buildRegistrationVerificationMessage(String firstName, String lastName, String randomVerification) {
        return new StringBuilder().append("Dear ").append(firstName).append(" ").append(lastName).append(" ,").append("your verification code is: ").append(randomVerification).toString();
    }

    @POST
    @Path("/{userId}/resetPassword")
    public Response sendChangePasswordVerification(@PathParam("userId") Integer userId){
        ThrottleSecurity.getInstance().throttle(userId);
        //issue verify code


        return Response.status(200).build();
    }


    @POST
    @Path("/{userId}/resetPassword/{verificationCode}")
    public Response changePassword(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, String password){
        //verify verification code
        //update user

        return Response.status(200).build();
    }
}