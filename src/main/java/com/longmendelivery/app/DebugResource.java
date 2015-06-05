package com.longmendelivery.app;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/debug")
public class DebugResource {
    @PUT
    @Path("testSMS/{to}/{body}")
    public Response getMessage(@PathParam("to") String to, @PathParam("body") String body) throws DependentServiceException {
        String output = "Testing SMS to : " + to + " with body: " + body;
        new TwilioSMSClient().sendSMS(to, body);
        return Response.status(200).entity(output).build();
    }
}