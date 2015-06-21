package com.longmendelivery.app;

import com.longmendelivery.app.util.ResourceResponseUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/ping")
public class EchoResource {
    @GET
    public Response getMessage() {
        String output = "Ping Health OK";
        return ResourceResponseUtil.generateOKMessage(output);
    }

    @GET
    @Path("/{param}")
    public Response getMessage(@PathParam("param") String message) {
        String output = "Ping Health OK, Echo: " + message;
        return ResourceResponseUtil.generateOKMessage(output);
    }
}