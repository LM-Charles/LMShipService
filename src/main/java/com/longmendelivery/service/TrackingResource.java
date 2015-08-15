package com.longmendelivery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RocketShipShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/tracking")
@Produces("application/json")
public class TrackingResource {
    private RocketShipShipmentClient client;

    public TrackingResource() throws DependentServiceException {
        client = new RocketShipShipmentClient();
    }

    @GET
    public Response getRate(@QueryParam("trackingNumber") String trackingNumber, @QueryParam("courier") String courierCode, @QueryParam("token") String token) throws DependentServiceException {
        CourierType type = CourierType.valueOf(courierCode);
        JsonNode trackingResult = client.getTracking(type, trackingNumber);

        return Response.status(Response.Status.OK).entity(trackingResult).build();
    }
}