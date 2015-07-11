package com.longmendelivery.lib.client.shipment.rocketshipit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.ShippingService;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ShippingDimension;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.TrackingRecord;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.UPSRateResponseEntry;
import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.RateScriptGenerator;
import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.RocketShipScriptEngine;
import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.TrackScriptGenerator;
import com.longmendelivery.service.model.AddressModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by desmond on 20/06/15.
 */
public class RocketShipShipmentClient implements ShipmentClient {
    private final RocketShipScriptEngine engine;

    public RocketShipShipmentClient() {
        this.engine = new RocketShipScriptEngine();
    }

    @Override
    public Map<ShippingService, BigDecimal> getAllRates(AddressModel sourceAddress, AddressModel destinationAddress, ShippingDimension dimension) throws DependentServiceException {
        Map<ShippingService, BigDecimal> rateMap = new TreeMap<>();

        for (CourierType type : CourierType.ALL) {
            RateScriptGenerator generator = new RateScriptGenerator(type);
            generator.withSourceAddress(sourceAddress);
            generator.withDestinationAddress(destinationAddress);
            generator.withDimensions(dimension);
            String script = generator.generate();
            List<UPSRateResponseEntry> result = engine.executeScript(script, new TypeReference<List<UPSRateResponseEntry>>() {
            });

            for (UPSRateResponseEntry entry : result) {
                rateMap.put(ShippingService.getFromServiceCode(entry.getServiceCode()), new BigDecimal(entry.getRate()));
            }
        }

        return rateMap;
    }

    @Override
    public TrackingRecord getTracking(CourierType type, String trackingNumber) {
        TrackScriptGenerator generator = new TrackScriptGenerator(type);
        generator.withTrackingNumber(trackingNumber);
        String script = generator.generate();
        //XXX
        return new TrackingRecord();
    }
}