package com.longmendelivery.app.util;

import com.longmendelivery.app.model.MessageResponseModel;
import com.longmendelivery.lib.security.NotAuthorizedException;

import javax.ws.rs.core.Response;

/**
 * Created by desmond on 20/06/15.
 */
public class ResourceResponseUtil {
    public static Response generateForbiddenMessage(NotAuthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(new MessageResponseModel(e.getResponseMessage())).build();
    }

    public static Response generateForbiddenMessage(String message) {
        return Response.status(Response.Status.FORBIDDEN).entity(new MessageResponseModel(message)).build();
    }

    public static Response generateOKMessage(String message) {
        return Response.status(Response.Status.OK).entity(new MessageResponseModel(message)).build();
    }

    public static Response generateNotFoundMessage(String message) {
        return Response.status(Response.Status.NOT_FOUND).entity(new MessageResponseModel(message)).build();
    }
}
