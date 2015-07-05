package com.longmendelivery.service.util;

import com.longmendelivery.lib.security.NotAuthorizedException;
import com.longmendelivery.service.model.response.MessageResponseModel;

import javax.ws.rs.core.Response;

/**
 * Created by  rabiddesireon 20/06/15.
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
