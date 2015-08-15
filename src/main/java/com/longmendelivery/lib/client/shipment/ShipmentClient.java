package com.longmendelivery.lib.client.shipment;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingDimension;
import com.longmendelivery.service.model.AddressModel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public interface ShipmentClient {
    Map<ShippingService, BigDecimal> getAllRates(AddressModel source, AddressModel destination, ShippingDimension dimension) throws DependentServiceException;

    JsonNode getTracking(CourierType type, String trackingNumber) throws DependentServiceException;
}
