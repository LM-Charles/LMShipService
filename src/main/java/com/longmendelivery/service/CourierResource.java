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
import java.util.*;

@Path("/courier")
@Produces("application/json")
@Component
public class CourierResource {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    public static final BigDecimal MAX_INSURANCE = new BigDecimal("2000");
    public static final BigDecimal INSURANCE_COMMISSION = new BigDecimal("0.035");
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
        RateResponseModel responseModel = calculateRate(order);

        if (responseModel == null) {
            return ResourceResponseUtil.generateNotFoundMessage("no rate available");
        }
        return Response.status(Response.Status.OK).entity(responseModel).build();
    }

    public RateResponseModel calculateRate(OrderCreationRequest order) throws DependentServiceException {
        Map<CourierServiceType, BigDecimal> totalRates = new HashMap<>();
        Map<CourierServiceType, Integer> totalRateCount = new HashMap<>();

        int numberOfBox = 0;
        for (ShipmentModel shipment : order.getShipments()) {
            if (ShipmentPackageType.BOXES.contains(shipment.getShipmentPackageType())) {
                numberOfBox++;
            }

            if (isLocalDelivery(order.getFromAddress().getCity(), order.getToAddress().getCity())) {
                Map<CourierServiceType, BigDecimal> longmenRate = longmenShipmentClient.getAllRates(order.getFromAddress(), order.getToAddress(), shipment);
                mergeResult(totalRates, totalRateCount, longmenRate);
            } else {
                Map<CourierServiceType, BigDecimal> packageRate = rsiClient.getAllRates(order.getFromAddress(), order.getToAddress(), shipment);
                mergeResult(totalRates, totalRateCount, packageRate);
            }
        }

        List<RateEntryModel> rates = new ArrayList<>();
        for (Map.Entry<CourierServiceType, BigDecimal> entry : totalRates.entrySet()) {
            if (totalRateCount.get(entry.getKey()) == order.getShipments().size()) {
                RateEntryModel rateEntry = new RateEntryModel(entry.getKey().getIconURL(), entry.getKey().getCategory(), entry.getValue(), entry.getValue().multiply(TAX_RATE), entry.getKey().getCourier().name(), entry.getKey().name());
                rates.add(rateEntry);
            }
            // do not include those with only a partial rate
        }

        if (rates.isEmpty()) {
            return null;
        }

        BigDecimal handlingCharge = new BigDecimal(numberOfBox);
        CourierServiceType longmenHandling = CourierServiceType.LONGMEN_HANDLING;
        RateEntryModel handling = new RateEntryModel(longmenHandling.getIconURL(), longmenHandling.getCategory(), handlingCharge, handlingCharge.multiply(TAX_RATE), longmenHandling.getCourier().name(), longmenHandling.getDescription());
        BigDecimal insuranceCharge = (order.getInsuranceValue().min(MAX_INSURANCE)).multiply(INSURANCE_COMMISSION);
        CourierServiceType longmenInsurance = CourierServiceType.LONGMEN_INSURANCE;
        RateEntryModel insurance = new RateEntryModel(longmenInsurance.getIconURL(), longmenInsurance.getCategory(), insuranceCharge, insuranceCharge.multiply(TAX_RATE), longmenInsurance.getCourier().name(), longmenInsurance.getDescription());

        RateResponseModel responseModel = new RateResponseModel(DateTime.now(), rates, handling, insurance);

        return responseModel;
    }

    private boolean isLocalDelivery(String sourceCity, String destinationCity) {
        Set<String> citiesInVan = new HashSet<>();
        citiesInVan.add("VANCOUVER");
        citiesInVan.add("NORTH VANCOUVER");
        citiesInVan.add("WEST VANCOUVER");
        citiesInVan.add("RICHMOND");
        citiesInVan.add("SURREY");
        citiesInVan.add("DELTA");
        citiesInVan.add("BURNABY");
        citiesInVan.add("COQUITLAM");
        return (citiesInVan.contains(sourceCity.toUpperCase()) && citiesInVan.contains(destinationCity.toUpperCase()));
    }

    private void mergeResult(Map<CourierServiceType, BigDecimal> totalRates, Map<CourierServiceType, Integer> totalRateCount, Map<CourierServiceType, BigDecimal> packageRate) {
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
    }
}