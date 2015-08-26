package com.longmendelivery.lib.client.shipment.rocketshipit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.engine.RSIScriptEngine;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.RateScriptGenerator;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.TrackScriptGenerator;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.DimensionModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.CourierType;
import com.longmendelivery.service.model.shipment.RSIRateEntry;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class RSIShipmentClient implements ShipmentClient {
    private final RSIScriptEngine engine;

    public RSIShipmentClient() throws DependentServiceException {
        this.engine = new RSIScriptEngine();
    }

    @Override
    public Map<CourierServiceType, BigDecimal> getAllRates(AddressModel sourceAddress, AddressModel destinationAddress, DimensionModel dimension) throws DependentServiceException {
        Map<CourierServiceType, BigDecimal> rateMap = new TreeMap<>();

        for (CourierType type : CourierType.ENABLED) {
            RateScriptGenerator generator = new RateScriptGenerator(type);
            generator.withSourceAddress(sourceAddress);
            generator.withDestinationAddress(destinationAddress);
            generator.withDimensions(dimension);
            String script = generator.generate();
            List<RSIRateEntry> result = engine.executeScript(script, new TypeReference<List<RSIRateEntry>>() {
            });


            for (RSIRateEntry entry : result) {
                rateMap.put(CourierServiceType.getFromServiceCode(type, entry.getServiceCode()), new BigDecimal(entry.getRate()));
            }
        }

        return rateMap;
    }

    @Override
    public ShipmentTrackingModel getTracking(CourierType type, String trackingNumber) throws DependentServiceException, ResourceNotFoundException {
        TrackScriptGenerator generator = new TrackScriptGenerator(type);
        generator.withTrackingNumber(trackingNumber);
        String script = generator.generate();
        JsonNode result = engine.executeScriptToTree(script);

        return type.getTrackingResponseParser().parseResponse(result);
    }
}
