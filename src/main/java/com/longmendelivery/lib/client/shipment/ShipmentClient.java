package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.CourierType;
import com.longmendelivery.service.model.shipment.ShipmentModel;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by rabiddesire on 04/06/15.
 */
public interface ShipmentClient {
    Map<CourierServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, ShipmentModel shipmentModel) throws DependentServiceException;

    ShipmentTrackingModel getTracking(CourierType type, String trackingNumber) throws DependentServiceException, ResourceNotFoundException;
}
