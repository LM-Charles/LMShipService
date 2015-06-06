package com.longmendelivery.app;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.model.Shipment;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/order")
public class OrderResource {
    @GET
    public Response listOrdersForUser(@QueryParam("userId") String userId, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);

        //list all active and recent order for users
        return Response.status(200).build();
    }

    @POST
    public Response createOrder(String order, @QueryParam("token") String token){
        String userId = fetchOrder(order).getClientId().getId();
        TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);

        return Response.status(200).build();
    }

    private Shipment fetchOrder(String orderId) {
        //XXX persistence layer
        return null;
    }


    @GET
    @Path("/{orderId}")
    public Response getOrderDetails(@PathParam("orderId") String orderId, String status, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.PUBLIC_READ);

        return Response.status(200).build();

    }

    @POST
    @Path("/{orderId}/status")
    public Response updateOrderStatus(@PathParam("orderId") String orderId, String status, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        return Response.status(200).build();

    }

    @POST
    @Path("/{orderId}/tracking")
    public Response addTrackingNumber(@PathParam("orderId") String orderId, String trackingNumber, String trackingDocument, @QueryParam("token") String token){
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        return Response.status(200).build();

    }

}