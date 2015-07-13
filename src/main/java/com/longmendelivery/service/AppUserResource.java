package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.exceptions.DependentServiceRequestException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.entity.AppUserGroupEntity;
import com.longmendelivery.persistence.entity.AppUserStatusEntity;
import com.longmendelivery.persistence.util.HibernateUtil;
import com.longmendelivery.service.model.AppUserModel;
import com.longmendelivery.service.model.VerificationCodeModel;
import com.longmendelivery.service.model.request.ChangeUserDetailRequestModel;
import com.longmendelivery.service.model.request.RegisterRequestModel;
import com.longmendelivery.service.security.NotAuthorizedException;
import com.longmendelivery.service.security.*;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
@Produces("application/json")
public class AppUserResource {
    @SuppressWarnings("unchecked")
    @GET
    public Response listUsers(@QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            List<AppUserEntity> users = session.createCriteria(AppUserEntity.class).list();
            List<AppUserModel> usersModel = new ArrayList<>();
            for (AppUserEntity user : users) {
                usersModel.add(DozerBeanMapperSingletonWrapper.getInstance().map(user, AppUserModel.class));
            }
            return Response.status(Response.Status.OK).entity(usersModel).build();
        } finally {
            session.close();
        }
    }

    @PUT
    @Consumes("application/json")
    public Response register(RegisterRequestModel registerRequestModel) {
        ThrottleSecurity.getInstance().throttle();
        String passwordMD5 = SecurityUtil.md5(registerRequestModel.getPassword());
        AppUserGroupEntity userGroup = AppUserGroupEntity.APP_USER;
        AppUserStatusEntity status = AppUserStatusEntity.NEW;
        AppUserEntity newUser = new AppUserEntity(registerRequestModel.getPhone(), registerRequestModel.getEmail(), passwordMD5, userGroup, status);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = writeSession.beginTransaction();
            try {
                Integer userId = (Integer) writeSession.save(newUser);
            } catch (ConstraintViolationException e) {
                return ResourceResponseUtil.generateConflictMessage("user already registered");
            }
            tx.commit();
            AppUserModel newUserModel = DozerBeanMapperSingletonWrapper.getInstance().map(newUser, AppUserModel.class);
            sanitizeUserModel(newUserModel);
            return Response.status(Response.Status.OK).entity(newUserModel).build();
        } finally {
            writeSession.close();
        }
    }

    private void sanitizeUserModel(AppUserModel newUserModel) {
        newUserModel.setApiToken("hidden");
        newUserModel.setPassword_md5("hidden");
        newUserModel.setVerificationCode(new VerificationCodeModel("hidden", DateTime.now()));
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        AppUserEntity user = (AppUserEntity) session.get(AppUserEntity.class, userId);
        if (user == null) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }
        AppUserModel userModel = DozerBeanMapperSingletonWrapper.getInstance().map(user, AppUserModel.class);
        sanitizeUserModel(userModel);
        return Response.status(Response.Status.OK).entity(userModel).build();
    }


    @GET
    @Path("/{userId}/admin")
    public Response getUserAdmin(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            AppUserEntity user = (AppUserEntity) session.get(AppUserEntity.class, userId);
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }
            AppUserModel userModel = DozerBeanMapperSingletonWrapper.getInstance().map(user, AppUserModel.class);
            tx.rollback();
            return Response.status(Response.Status.OK).entity(userModel).build();
        } finally {
            session.close();
        }
    }

    @POST
    @Path("/{userId}")
    public Response changeUserDetail(@PathParam("userId") Integer userId, @QueryParam("token") String token, ChangeUserDetailRequestModel request) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }


        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{userId}/activation")
    public Response sendActivationVerification(@PathParam("userId") Integer userId, @QueryParam("phone") String phone, @QueryParam("token") String token) throws DependentServiceException {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        String randomVerification = SecurityUtil.generateSecureVerificationCode();

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            TwilioSMSClient twilioSMSClient = new TwilioSMSClient();

            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }
            if (phone != null && !phone.equals(user.getPhone())) {
                user.setPhone(phone);
            }
            user.setUserStatus(AppUserStatusEntity.PENDING_VERIFICATION_REGISTER);
            user.setVerificationString(randomVerification);
            writeSession.update(user);
            tx.commit();

            String smsBody = buildRegistrationVerificationMessage(user.getEmail(), randomVerification);
            try {
                twilioSMSClient.sendSMS(user.getPhone(), smsBody);
            } catch (DependentServiceRequestException e) {
                return ResourceResponseUtil.generateBadRequestMessage("Unable to activate due to invalid phone number: " + phone);
            }
            return ResourceResponseUtil.generateOKMessage("Register verification sent for " + user.getEmail() + " to " + user.getPhone());
        } finally {
            writeSession.close();
        }
    }

    @POST
    @Path("/{userId}/activation/{verificationCode}")
    public Response activate(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }

            if (!user.getUserStatus().equals(AppUserStatusEntity.PENDING_VERIFICATION_REGISTER)) {
                return ResourceResponseUtil.generateForbiddenMessage("User is not pending verification");
            } else if (user.getVerificationString().equals(verificationCode)) {
                user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
                user.setUserStatus(AppUserStatusEntity.ACTIVE);
                writeSession.update(user);
                tx.commit();
                return Response.status(Response.Status.OK).entity("User activated").build();
            } else {
                return ResourceResponseUtil.generateForbiddenMessage("Verification code not accepted");
            }
        } finally {
            writeSession.close();
        }
    }

    private String buildRegistrationVerificationMessage(String username, String randomVerification) {
        return new StringBuilder().append("Dear customer (").append(username).append("), your verification code is: ").append(randomVerification).toString();
    }

    @POST
    @Path("/{userId}/resetPassword")
    public Response sendChangePasswordVerification(@PathParam("userId") Integer userId) throws DependentServiceException, DependentServiceRequestException {
        ThrottleSecurity.getInstance().throttle(userId);

        String randomVerification = SecurityUtil.generateSecureVerificationCode();
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }
            if (user.getUserStatus().equals(AppUserStatusEntity.DISABLED)) {
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("Cannot request password change of disabled user").build();
            } else {
                user.setVerificationString(randomVerification);
                writeSession.update(user);
                tx.commit();

                String smsBody = buildRegistrationVerificationMessage(user.getEmail(), randomVerification);
                new TwilioSMSClient().sendSMS(user.getPhone(), smsBody);
                return ResourceResponseUtil.generateOKMessage("Password verification sent for " + user.getEmail() + " to " + user.getPhone());
            }
        } finally {
            writeSession.close();
        }
    }


    @POST
    @Path("/{userId}/resetPassword/{verificationCode}")
    public Response changePassword(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("newPassword") String password) {
        ThrottleSecurity.getInstance().throttle(userId);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }

            if (user.getUserStatus().equals(AppUserStatusEntity.DISABLED)) {
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("Cannot request password change of disabled user").build();
            } else if (user.getVerificationString().equals(verificationCode)) {
                user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
                user.setPassword_md5(SecurityUtil.md5(password));
                user.setApiToken(SecurityUtil.generateSecureToken());
                writeSession.update(user);
                tx.commit();
                return ResourceResponseUtil.generateOKMessage("Password changed, token refreshed");
            } else {
                return ResourceResponseUtil.generateForbiddenMessage("Verification code not accepted");
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
            if (user == null) {
                return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
            }

            user.setVerificationString(SecurityUtil.generateSecureVerificationCode());
            user.setPassword_md5(SecurityUtil.md5(SecurityUtil.generateSecureToken()));
            user.setApiToken(SecurityUtil.generateSecureToken());
            user.setUserStatus(AppUserStatusEntity.DISABLED);
            writeSession.update(user);
            tx.commit();
            return ResourceResponseUtil.generateOKMessage("Password changed, token refreshed");
        } finally {
            writeSession.close();
        }
    }
}