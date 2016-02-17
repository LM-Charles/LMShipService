package com.longmendelivery.service.util;

import com.longmendelivery.service.model.GeneralMessageResponseModel;
import com.longmendelivery.service.security.NotAuthorizedException;

import javax.ws.rs.core.Response;

/**
 * Created by rabiddesire on 20/06/15.
 */
public class ResourceResponseUtil {
    public static Response generateForbiddenMessage(NotAuthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(new GeneralMessageResponseModel(e.getLocalizedMessage())).build();
    }

    public static Response generateForbiddenMessage(String message) {
        return Response.status(Response.Status.FORBIDDEN).entity(new GeneralMessageResponseModel(message)).build();
    }

    public static Response generateOKMessage(String message) {
        return Response.status(Response.Status.OK).entity(new GeneralMessageResponseModel(message)).build();
    }

    public static Response generateNotFoundMessage(String message) {
        return Response.status(Response.Status.NOT_FOUND).entity(new GeneralMessageResponseModel(message)).build();
    }

    public static Response generateBadRequestMessage(String message) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new GeneralMessageResponseModel(message)).build();
    }

    public static Response generateConflictMessage(String message) {
        return Response.status(Response.Status.CONFLICT).entity(new GeneralMessageResponseModel(message)).build();
    }

    public static Response generateServiceMessage(String message) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new GeneralMessageResponseModel(message)).build();
    }
}
