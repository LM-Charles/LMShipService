package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.longmendelivery.LongmenShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.OrderCreationRequest;
import com.longmendelivery.service.model.order.RateEntryModel;
import com.longmendelivery.service.model.shipment.*;
import com.longmendelivery.service.security.ThrottleSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RSIShipmentClient rsiClient;
    @Autowired
    private LongmenShipmentClient longmenShipmentClient;

    public CourierResource() throws DependentServiceException {
    }

    @GET
    @Path("/tracking")
    public Response getTracking(@QueryParam("trackingNumber") String trackingNumber, @QueryParam("courier") String courierCode, @QueryParam("token") String token) throws DependentServiceException {

        try {
            CourierType type = CourierType.valueOf(courierCode);
            ShipmentTrackingModel trackingResult;
            if (type.getShipmentClient().equals(LongmenShipmentClient.class)) {
                trackingResult = longmenShipmentClient.getTracking(type, trackingNumber);
            } else {
                trackingResult = rsiClient.getTracking(type, trackingNumber);
            }
            return Response.status(Response.Status.OK).entity(trackingResult).build();

        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage(e.getLocalizedMessage());
        }
    }

    @POST
    @Path("/rate")
    public Response calculateRate(OrderCreationRequest order, @QueryParam("token") String token) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(order.hashCode());
        Map<CourierServiceType, BigDecimal> totalRates = new HashMap<>();
        Map<CourierServiceType, Integer> totalRateCount = new HashMap<>();

        int numberOfBox = 0;
        for (ShipmentModel shipment : order.getShipments()) {
            if (ShipmentPackageType.BOXES.contains(shipment.getShipmentPackageType())) {
                numberOfBox++;
            }
            Map<CourierServiceType, BigDecimal> packageRate = rsiClient.getAllRates(order.getFromAddress(), order.getToAddress(), shipment);
            for (Map.Entry<CourierServiceType, BigDecimal> entry : packageRate.entrySet()) {
                BigDecimal oldTotal = totalRates.get(entry.getKey());
                if (oldTotal == null) {
                    totalRates.put(entry.getKey(), entry.getValue());
                    totalRateCount.put(entry.getKey(), 1);
                } else {
                    totalRates.put(entry.getKey(), oldTotal.add(entry.getValue()));
                    totalRateCount.put(entry.getKey(), totalRateCount.get(entry.getKey()) + 1);

                }
            }

            Map<CourierServiceType, BigDecimal> longmenRate = longmenShipmentClient.getAllRates(order.getFromAddress(), order.getToAddress(), shipment);
            for (Map.Entry<CourierServiceType, BigDecimal> entry : longmenRate.entrySet()) {
                BigDecimal oldTotal = totalRates.get(entry.getKey());
                if (oldTotal == null) {
                    totalRates.put(entry.getKey(), entry.getValue());
                    totalRateCount.put(entry.getKey(), 1);
                } else {
                    totalRates.put(entry.getKey(), oldTotal.add(entry.getValue()));
                    totalRateCount.put(entry.getKey(), totalRateCount.get(entry.getKey()) + 1);
                }
            }
        }

        List<RateEntryModel> rates = new ArrayList<>();
        for (Map.Entry<CourierServiceType, BigDecimal> entry : totalRates.entrySet()) {
            if (totalRateCount.get(entry.getKey()) == order.getShipments().size()) {
                RateEntryModel rateEntry = new RateEntryModel("", entry.getKey().getCategory(), entry.getValue(), entry.getKey().getCourier().name(), entry.getKey().name(), DateTime.now());
                rates.add(rateEntry);
            }
            // do not include those with only a partial rate
        }

        BigDecimal handlingCharge = new BigDecimal(numberOfBox);
        RateEntryModel handling = new RateEntryModel("", "HANDLING", handlingCharge, "LM_DELIVERY", "HANDLING", DateTime.now());
        BigDecimal insuranceCharge = (order.getInsuranceValue().min(new BigDecimal("2000"))).multiply(new BigDecimal("0.035"));
        RateEntryModel insurance = new RateEntryModel("", "INSURANCE", insuranceCharge, "LM_DELIVERY", "INSURANCE", DateTime.now());

        RateResponseModel responseModel = new RateResponseModel(DateTime.now(), rates, handling, insurance);

        return Response.status(Response.Status.OK).entity(responseModel).build();
    }
}