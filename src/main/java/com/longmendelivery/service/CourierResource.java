package com.longmendelivery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RocketShipShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ServiceType;
import com.longmendelivery.service.model.PackageDimensionModel;
import com.longmendelivery.service.model.RateEntryModel;
import com.longmendelivery.service.model.ShipmentModel;
import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;
import com.longmendelivery.service.security.ThrottleSecurity;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/courier")
@Produces("application/json")
public class CourierResource {
    private RocketShipShipmentClient client;

    public CourierResource() throws DependentServiceException {
        client = new RocketShipShipmentClient();
    }

    @GET
    @Path("/tracking")
    public Response getTracking(@QueryParam("trackingNumber") String trackingNumber, @QueryParam("courier") String courierCode, @QueryParam("token") String token) throws DependentServiceException {
        CourierType type = CourierType.valueOf(courierCode);
        JsonNode trackingResult = client.getTracking(type, trackingNumber);

        return Response.status(Response.Status.OK).entity(trackingResult).build();
    }

    @POST
    @Path("/rate")
    public Response calculateRate(RateRequestModel rateRequestModel, @QueryParam("token") String token) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(rateRequestModel.hashCode());

        Map<ServiceType, BigDecimal> totalRates = new HashMap<>();
        for (ShipmentModel shipment : rateRequestModel.getShipments()) {
            PackageDimensionModel dimension = new PackageDimensionModel(shipment.getLength(), shipment.getWidth(), shipment.getHeight(), shipment.getWeight());
            Map<ServiceType, BigDecimal> packageRate = client.getAllRates(rateRequestModel.getFromAddress(), rateRequestModel.getToAddress(), dimension);
            for (Map.Entry<ServiceType, BigDecimal> entry : packageRate.entrySet()) {
                BigDecimal oldTotal = totalRates.get(entry.getKey());
                if (oldTotal == null) {
                    totalRates.put(entry.getKey(), entry.getValue());
                } else {
                    totalRates.put(entry.getKey(), oldTotal.add(entry.getValue()));
                }

            }
        }

        List<RateEntryModel> rates = new ArrayList<>();
        for (Map.Entry<ServiceType, BigDecimal> entry : totalRates.entrySet()) {
            RateEntryModel rateEntry = new RateEntryModel("", entry.getKey().name(), entry.getValue(), entry.getKey().name(), entry.getKey().name(), DateTime.now());
            rates.add(rateEntry);
        }

        RateResponseModel responseModel = new RateResponseModel(DateTime.now(), rates, new RateEntryModel("", "HANDLING", BigDecimal.ONE, "HANDLING", "HANDLING", DateTime.now()));

        return Response.status(Response.Status.OK).entity(responseModel).build();
    }
}