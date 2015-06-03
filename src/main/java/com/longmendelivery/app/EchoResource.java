package com.longmendelivery.app;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/ping")
public class EchoResource {
    @GET
    public Response getMessage() {
        String output = "Ping Health OK";
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/{param}")
    public Response getMessage(@PathParam("param") String message) {
        String output = "Ping Health OK, Echo: " + message;
        return Response.status(200).entity(output).build();
    }
}