package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.shipment.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class DummyShipmentClient implements ShipmentClient {
    @Override
    public Map<ShippingService, BigDecimal> getRates(ShippingAddress shippingAddress, ShippingDimension dimension) {
        HashMap<ShippingService, BigDecimal> rateMap = new HashMap<>();
        rateMap.put(ShippingService.DUMMY, BigDecimal.ONE);
        return rateMap;
    }

    @Override
    public TrackingRecord getTracking(String trackingNumber, TrackingDocumentType trackingDocumentType) {
        return null;
    }
}
