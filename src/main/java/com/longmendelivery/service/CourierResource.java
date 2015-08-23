package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.courier.CourierServiceType;
import com.longmendelivery.service.model.courier.CourierType;
import com.longmendelivery.service.model.order.PackageDimensionModel;
import com.longmendelivery.service.model.order.RateEntryModel;
import com.longmendelivery.service.model.order.ShipmentModel;
import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import com.longmendelivery.service.security.ThrottleSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
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
    private RSIShipmentClient client;

    public CourierResource() throws DependentServiceException {
        client = new RSIShipmentClient();
    }

    @GET
    @Path("/tracking")
    public Response getTracking(@QueryParam("trackingNumber") String trackingNumber, @QueryParam("courier") String courierCode, @QueryParam("token") String token) throws DependentServiceException {

        try {
            CourierType type = CourierType.valueOf(courierCode);
            ShipmentTrackingResponseModel trackingResult = client.getTracking(type, trackingNumber);
            return Response.status(Response.Status.OK).entity(trackingResult).build();

        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage(e.getLocalizedMessage());
        }
    }

    @POST
    @Path("/rate")
    public Response calculateRate(RateRequestModel rateRequestModel, @QueryParam("token") String token) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(rateRequestModel.hashCode());

        Map<CourierServiceType, BigDecimal> totalRates = new HashMap<>();
        for (ShipmentModel shipment : rateRequestModel.getShipments()) {
            PackageDimensionModel dimension = new PackageDimensionModel(shipment.getLength(), shipment.getWidth(), shipment.getHeight(), shipment.getWeight());
            Map<CourierServiceType, BigDecimal> packageRate = client.getAllRates(rateRequestModel.getFromAddress(), rateRequestModel.getToAddress(), dimension);
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