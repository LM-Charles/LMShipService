package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.DimensionModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.CourierType;
import com.longmendelivery.service.model.shipment.ShipmentTrackingResponse;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by desmond on 04/06/15.
 */
public interface ShipmentClient {
    Map<CourierServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, DimensionModel dimension) throws DependentServiceException;

    ShipmentTrackingResponse getTracking(CourierType type, String trackingNumber) throws DependentServiceException, ResourceNotFoundException;
}
