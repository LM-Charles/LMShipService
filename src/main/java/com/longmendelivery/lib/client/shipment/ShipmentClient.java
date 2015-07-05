package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public interface ShipmentClient {
    Map<ShippingService, BigDecimal> getRates(ShippingAddress shippingAddress, ShippingDimension dimension);

    TrackingRecord getTracking(String trackingNumber, TrackingDocumentType trackingDocumentType);
}
