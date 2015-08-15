package com.longmendelivery.lib.client.shipment.rocketshipit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.engine.ScriptEngine;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.ServiceType;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.UPSRateResponseEntry;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.RateScriptGenerator;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.TrackScriptGenerator;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.PackageDimensionModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class RocketShipShipmentClient implements ShipmentClient {
    private final ScriptEngine engine;

    public RocketShipShipmentClient() throws DependentServiceException {
        this.engine = new ScriptEngine();
    }

    @Override
    public Map<ServiceType, BigDecimal> getAllRates(AddressModel sourceAddress, AddressModel destinationAddress, PackageDimensionModel dimension) throws DependentServiceException {
        Map<ServiceType, BigDecimal> rateMap = new TreeMap<>();

        for (CourierType type : CourierType.ENABLED) {
            RateScriptGenerator generator = new RateScriptGenerator(type);
            generator.withSourceAddress(sourceAddress);
            generator.withDestinationAddress(destinationAddress);
            generator.withDimensions(dimension);
            String script = generator.generate();
            JsonNode tree = engine.executeScriptToTree(script);
            System.out.println(tree.textValue());
            List<UPSRateResponseEntry> result = engine.executeScript(script, new TypeReference<List<UPSRateResponseEntry>>() {
            });


            for (UPSRateResponseEntry entry : result) {
                rateMap.put(ServiceType.getFromServiceCode(type, entry.getServiceCode()), new BigDecimal(entry.getRate()));
            }
        }

        return rateMap;
    }

    @Override
    public JsonNode getTracking(CourierType type, String trackingNumber) throws DependentServiceException {
        TrackScriptGenerator generator = new TrackScriptGenerator(type);
        generator.withTrackingNumber(trackingNumber);
        String script = generator.generate();
        JsonNode result = engine.executeScriptToTree(script);

        return result;
    }
}
