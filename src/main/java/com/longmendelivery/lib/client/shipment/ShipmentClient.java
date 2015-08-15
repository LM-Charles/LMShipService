package com.longmendelivery.lib.client.shipment;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ServiceType;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.PackageDimensionModel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public interface ShipmentClient {
    Map<ServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, PackageDimensionModel dimension) throws DependentServiceException;

    JsonNode getTracking(CourierType type, String trackingNumber) throws DependentServiceException;
}
