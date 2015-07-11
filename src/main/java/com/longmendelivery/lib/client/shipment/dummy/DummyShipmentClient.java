package com.longmendelivery.lib.client.shipment.dummy;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.ShippingService;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingDimension;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.TrackingRecord;
import com.longmendelivery.service.model.AddressModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class DummyShipmentClient implements ShipmentClient {
    @Override
    public Map<ShippingService, BigDecimal> getAllRates(AddressModel source, AddressModel destination, ShippingDimension dimension) throws DependentServiceException {
        HashMap<ShippingService, BigDecimal> rateMap = new HashMap<>();
        rateMap.put(ShippingService.DUMMY, BigDecimal.ONE);
        return rateMap;
    }

    @Override
    public TrackingRecord getTracking(CourierType type, String trackingNumber) {
        return new TrackingRecord();
    }
}
