package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.exceptions.DependentServiceRequestException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.request.ChangeUserDetailRequestModel;
import com.longmendelivery.service.model.request.RegisterRequestModel;
import com.longmendelivery.service.model.user.AppUserGroupType;
import com.longmendelivery.service.model.user.AppUserModel;
import com.longmendelivery.service.model.user.AppUserStatusType;
import com.longmendelivery.service.security.NotAuthorizedException;
import com.longmendelivery.service.security.*;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
@Produces("application/json")
@Component
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = true)
public class AppUserResource {
    @Autowired
    private UserStorage userStorage;
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    @SuppressWarnings("unchecked")
    @GET
    public Response listUsers(@QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);
        List<AppUserEntity> users = userStorage.listAll(Integer.MAX_VALUE, 0);
        List<AppUserModel> usersModel = new ArrayList<>();
        for (AppUserEntity user : users) {
            usersModel.add(mapper.map(user, AppUserModel.class));
        }
        return Response.status(Response.Status.OK).entity(usersModel).build();
    }

    @PUT
    @Consumes("application/json")
    @Transactional(readOnly = false)
    public Response register(RegisterRequestModel registerRequestModel) {
        ThrottleSecurity.getInstance().throttle();
        String passwordMD5 = SecurityUtil.md5(registerRequestModel.getPassword());
        AppUserGroupType userGroup = AppUserGroupType.APP_USER;
        AppUserStatusType status = AppUserStatusType.NEW;
        AppUserEntity newUser = new AppUserEntity(registerRequestModel.getPhone(), registerRequestModel.getEmail(), passwordMD5, userGroup, status);

        try {
            userStorage.create(newUser);
            AppUserModel newUserModel = mapper.map(newUser, AppUserModel.class);
            sanitizeUserModel(newUserModel);
            return Response.status(Response.Status.OK).entity(newUserModel).build();
        } catch (ConstraintViolationException e) {
            return ResourceResponseUtil.generateConflictMessage("user already registered");
        }
    }

    private void sanitizeUserModel(AppUserModel newUserModel) {
        newUserModel.setApiToken("hidden");
        newUserModel.setPassword_md5("hidden");
        newUserModel.setVerificationCode("hidden");
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        }

        try {
            AppUserEntity user = userStorage.get(userId);
            AppUserModel userModel = mapper.map(user, AppUserModel.class);
            sanitizeUserModel(userModel);
            return Response.status(Response.Status.OK).entity(userModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);

        }
    }


    @GET
    @Path("/{userId}/admin")
    public Response getUserAdmin(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            AppUserEntity user = userStorage.get(userId);
            AppUserModel userModel = mapper.map(user, AppUserModel.class);
            return Response.status(Response.Status.OK).entity(userModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);

        }
    }

    @POST
    @Path("/{userId}")
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public Response sendActivationVerification(@PathParam("userId") Integer userId, @QueryParam("phone") String phone, @QueryParam("token") String token) throws DependentServiceException {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        String randomVerification = SecurityUtil.generateSecureVerificationCode();

        TwilioSMSClient twilioSMSClient = new TwilioSMSClient();

        try {
            AppUserEntity user = userStorage.get(userId);
            if (phone != null && !phone.equals(user.getPhone())) {
                user.setPhone(phone);
            }
            user.setUserStatus(AppUserStatusType.PENDING_VERIFICATION_REGISTER);
            user.setVerificationCode(randomVerification);
            userStorage.update(user);
            String smsBody = buildRegistrationVerificationMessage(user.getEmail(), randomVerification);
            try {
                twilioSMSClient.sendSMS(user.getPhone(), smsBody);
            } catch (DependentServiceRequestException e) {
                return ResourceResponseUtil.generateBadRequestMessage("Unable to activate due to invalid phone number: " + phone);
            }
            return ResourceResponseUtil.generateOKMessage("Register verification sent for " + user.getEmail() + " to " + user.getPhone());
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }
    }

    @POST
    @Path("/{userId}/activation/{verificationCode}")
    @Transactional(readOnly = false)
    public Response activate(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }


        AppUserEntity user = null;
        try {
            user = userStorage.get(userId);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }

        if (!user.getUserStatus().equals(AppUserStatusType.PENDING_VERIFICATION_REGISTER)) {
            return ResourceResponseUtil.generateForbiddenMessage("User is not pending verification");
        } else if (user.getVerificationCode().equals(verificationCode)) {
            user.setVerificationCode(SecurityUtil.generateSecureVerificationCode());
            user.setUserStatus(AppUserStatusType.ACTIVE);
            userStorage.update(user);
            return ResourceResponseUtil.generateOKMessage("User activated");
        } else {
            return ResourceResponseUtil.generateForbiddenMessage("Verification code not accepted");
        }
    }

    private String buildRegistrationVerificationMessage(String username, String randomVerification) {
        return new StringBuilder().append("Dear customer (").append(username).append("), your verification code is: ").append(randomVerification).toString();
    }

    @POST
    @Path("/{userId}/resetPassword")
    @Transactional(readOnly = false)
    public Response sendChangePasswordVerification(@PathParam("userId") Integer userId) throws DependentServiceException, DependentServiceRequestException {
        ThrottleSecurity.getInstance().throttle(userId);

        String randomVerification = SecurityUtil.generateSecureVerificationCode();


        AppUserEntity user = null;
        try {
            user = userStorage.get(userId);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }

        if (user.getUserStatus().equals(AppUserStatusType.DISABLED)) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity("Cannot request password change of disabled user").build();
        } else {
            user.setVerificationCode(randomVerification);
            userStorage.update(user);

            String smsBody = buildRegistrationVerificationMessage(user.getEmail(), randomVerification);
            new TwilioSMSClient().sendSMS(user.getPhone(), smsBody);
            return ResourceResponseUtil.generateOKMessage("Password verification sent for " + user.getEmail() + " to " + user.getPhone());
        }
    }


    @POST
    @Path("/{userId}/resetPassword/{verificationCode}")
    @Transactional(readOnly = false)
    public Response changePassword(@PathParam("userId") Integer userId, @PathParam("verificationCode") String verificationCode, @QueryParam("newPassword") String password) {
        ThrottleSecurity.getInstance().throttle(userId);


        AppUserEntity user = null;
        try {
            user = userStorage.get(userId);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }
        if (user.getUserStatus().equals(AppUserStatusType.DISABLED)) {
            return ResourceResponseUtil.generateBadRequestMessage("Cannot request password change of disabled user");
        } else if (user.getVerificationCode().equals(verificationCode)) {
            user.setVerificationCode(SecurityUtil.generateSecureVerificationCode());
            user.setPassword_md5(SecurityUtil.md5(password));
            user.setApiToken(SecurityUtil.generateSecureToken());
            userStorage.update(user);
            return ResourceResponseUtil.generateOKMessage("Password changed, token refreshed");
        } else {
            return ResourceResponseUtil.generateForbiddenMessage("Verification code not accepted");
        }
    }

    @DELETE
    @Path("/{userId}")
    @Transactional(readOnly = false)
    public Response disableUser(@PathParam("userId") Integer userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);


        AppUserEntity user = null;
        try {
            user = userStorage.get(userId);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("User not found for: " + userId);
        }

        user.setVerificationCode(SecurityUtil.generateSecureVerificationCode());
        user.setPassword_md5(SecurityUtil.md5(SecurityUtil.generateSecureToken()));
        user.setApiToken(SecurityUtil.generateSecureToken());
        user.setUserStatus(AppUserStatusType.DISABLED);
        userStorage.update(user);

        return ResourceResponseUtil.generateOKMessage("Password changed, token refreshed");
    }
}