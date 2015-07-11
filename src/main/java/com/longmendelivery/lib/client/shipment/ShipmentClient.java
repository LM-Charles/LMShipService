package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingAddress;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingDimension;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingService;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.TrackingRecord;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by desmond on 04/06/15.
 */
public interface ShipmentClient {
    Map<ShippingService, BigDecimal> getRates(ShippingAddress shippingAddress, ShippingDimension dimension);

    TrackingRecord getTracking(String trackingNumber);
}
