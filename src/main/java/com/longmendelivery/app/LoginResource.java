package com.longmendelivery.app;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.lib.security.TokenSecurity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
    @POST
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        ThrottleSecurity.getInstance().throttle(username);

        String token = TokenSecurity.getInstance().issueToken(username, password);
        return Response.status(200).entity(token).build();
    }

    @DELETE
    @Path("/{userId}")
    public Response logout(@PathParam("userId") String userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        TokenSecurity.getInstance().invalidateToken(userId);
        return Response.status(200).build();
    }
}