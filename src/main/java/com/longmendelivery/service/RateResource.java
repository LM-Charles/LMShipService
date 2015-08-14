package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShippingService;
import com.longmendelivery.lib.client.shipment.rocketshipit.RocketShipShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingDimension;
import com.longmendelivery.service.model.RateEntryModel;
import com.longmendelivery.service.model.ShipmentModel;
import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;
import com.longmendelivery.service.security.ThrottleSecurity;
import org.joda.time.DateTime;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/rate")
@Produces("application/json")
public class RateResource {
    private RocketShipShipmentClient client;

    public RateResource() throws DependentServiceException {
        client = new RocketShipShipmentClient();
    }
    @POST
    public Response getRate(RateRequestModel rateRequestModel, @QueryParam("token") String token) throws DependentServiceException {
        ThrottleSecurity.getInstance().throttle(rateRequestModel.hashCode());

        Map<ShippingService, BigDecimal> totalRates = new HashMap<>();
        for (ShipmentModel shipment : rateRequestModel.getShipments()) {
            ShippingDimension dimension = new ShippingDimension(shipment.getLength(), shipment.getWidth(), shipment.getHeight(), shipment.getWeight());
            Map<ShippingService, BigDecimal> packageRate = client.getAllRates(rateRequestModel.getFromAddress(), rateRequestModel.getToAddress(), dimension);
            for (Map.Entry<ShippingService, BigDecimal> entry : packageRate.entrySet()) {
                BigDecimal oldTotal = totalRates.get(entry.getKey());
                if (oldTotal.equals(BigDecimal.ZERO)) {
                    totalRates.put(entry.getKey(), entry.getValue());
                } else {
                    totalRates.put(entry.getKey(), oldTotal.add(entry.getValue()));
                }

            }
        }

        List<RateEntryModel> rates = new ArrayList<>();
        for (Map.Entry<ShippingService, BigDecimal> entry : totalRates.entrySet()) {
            RateEntryModel rateEntry = new RateEntryModel("", entry.getKey().name(), entry.getValue(), entry.getKey().name(), entry.getKey().name(), DateTime.now());
            rates.add(rateEntry);
        }

        RateResponseModel responseModel = new RateResponseModel(DateTime.now(), rates, null);

        return Response.status(Response.Status.OK).entity(responseModel).build();
    }
}