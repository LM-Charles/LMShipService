package com.longmendelivery.app;

import com.longmendelivery.lib.security.NotAuthorizedException;

import javax.ws.rs.core.Response;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class ResourceResponseUtil {
    public static Response generateErrorResponse(NotAuthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getResponseMessage()).build();
    }
}
