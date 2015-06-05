package com.longmendelivery.app;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Time;

@Path("/user")
public class AppUserResource {
    @GET
    public Response listUsers(@QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.ADMIN);

        //list all users
        return Response.status(200).build();
    }

    @GET
    @Path("/{userId}")
    public Response getUserDetail(@PathParam("userId") String userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);

        //get user information
        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}")
    public Response changeUserDetail(@PathParam("userId") String userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        //get user information
        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}/activation")
    public Response sendActivationVerification(@PathParam("userId") String userId, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        //issue verify code

        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}/activation/{verificationCode}")
    public Response activate(@PathParam("userId") String userId, @PathParam("verificationCode") String verificationCode, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        //verify verification code
        //update user

        return Response.status(200).build();
    }

    @POST
    @Path("/{userId}/resetPassword")
    public Response sendChangePasswordVerification(@PathParam("userId") String userId){
        ThrottleSecurity.getInstance().throttle(userId);
        //issue verify code


        return Response.status(200).build();
    }


    @POST
    @Path("/{userId}/resetPassword/{verificationCode}")
    public Response changePassword(@PathParam("userId") String userId, @PathParam("verificationCode") String verificationCode, String password){
        //verify verification code
        //update user

        return Response.status(200).build();
    }
}