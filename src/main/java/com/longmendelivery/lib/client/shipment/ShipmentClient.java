package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.courier.CourierServiceType;
import com.longmendelivery.service.model.courier.CourierType;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.PackageDimensionModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by desmond on 04/06/15.
 */
public interface ShipmentClient {
    Map<CourierServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, PackageDimensionModel dimension) throws DependentServiceException;

    ShipmentTrackingResponseModel getTracking(CourierType type, String trackingNumber) throws DependentServiceException, ResourceNotFoundException;
}
