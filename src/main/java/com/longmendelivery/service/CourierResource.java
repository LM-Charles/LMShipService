package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.DimensionModel;
import com.longmendelivery.service.model.order.RateEntryModel;
import com.longmendelivery.service.model.order.ShipmentModel;
import com.longmendelivery.service.model.shipment.*;
import com.longmendelivery.service.security.ThrottleSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/courier")
@Produces("application/json")
@Component
public class CourierResource {
    private RSIShipmentClient client;

    public CourierResource() throws DependentServiceException {
        client = new RSIShipmentClient();
    }

    @GET
    @Path("/tracking")
    public Response getTracking(@QueryParam("trackingNumber") String trackingNumber, @QueryParam("courier") String courierCode, @QueryParam("token") String token) throws DependentServiceException {

        try {
            CourierType type = CourierType.valueOf(courierCode);
            ShipmentTrackingResponse trackingResult = client.getTracking(type, trackingNumber);
            return Response.status(Response.Status.OK).entity(trackingResult).build();

        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage(e.getLocalizedMessage());
        }
    }

    @POST
    @Path("/rate")
    public Response calculateRate(RateRequest rateRequest, @QueryParam("token") String token) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(rateRequest.hashCode());

        Map<CourierServiceType, BigDecimal> totalRates = new HashMap<>();
        for (ShipmentModel shipment : rateRequest.getShipments()) {
            DimensionModel dimension = new DimensionModel(shipment.getLength(), shipment.getWidth(), shipment.getHeight(), shipment.getWeight());
            Map<CourierServiceType, BigDecimal> packageRate = client.getAllRates(rateRequest.getFromAddress(), rateRequest.getToAddress(), dimension);
            for (Map.Entry<CourierServiceType, BigDecimal> entry : packageRate.entrySet()) {
                BigDecimal oldTotal = totalRates.get(entry.getKey());
                if (oldTotal == null) {
                    totalRates.put(entry.getKey(), entry.getValue());
                } else {
                    totalRates.put(entry.getKey(), oldTotal.add(entry.getValue()));
                }

            }
        }

        List<RateEntryModel> rates = new ArrayList<>();
        for (Map.Entry<CourierServiceType, BigDecimal> entry : totalRates.entrySet()) {
            RateEntryModel rateEntry = new RateEntryModel("", entry.getKey().name(), entry.getValue(), entry.getKey().name(), entry.getKey().name(), DateTime.now());
            rates.add(rateEntry);
        }

        RateResponseModel responseModel = new RateResponseModel(DateTime.now(), rates, new RateEntryModel("", "HANDLING", BigDecimal.ONE, "HANDLING", "HANDLING", DateTime.now()));

        return Response.status(Response.Status.OK).entity(responseModel).build();
    }
}