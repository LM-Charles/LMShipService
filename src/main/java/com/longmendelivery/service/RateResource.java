package com.longmendelivery.service;

import com.longmendelivery.lib.security.ThrottleSecurity;
import com.longmendelivery.service.behavior.DummyRateCalculator;
import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/rate")
@Produces("application/json")
public class RateResource {
    @POST
    public Response createOrder(RateRequestModel rateRequestModel, @QueryParam("token") String token) {
        ThrottleSecurity.getInstance().throttle(rateRequestModel.hashCode());
        RateResponseModel rateModel = new DummyRateCalculator().calculate(rateRequestModel);
        return Response.status(Response.Status.OK).entity(rateModel).build();
    }
}