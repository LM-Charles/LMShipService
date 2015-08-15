package com.longmendelivery.lib.client.shipment.rocketshipit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.engine.RSIScriptEngine;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.RateScriptGenerator;
import com.longmendelivery.lib.client.shipment.rocketshipit.script.TrackScriptGenerator;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.CourierServiceType;
import com.longmendelivery.service.model.CourierType;
import com.longmendelivery.service.model.PackageDimensionModel;
import com.longmendelivery.service.model.response.CourierRateResponseModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;

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
    public Map<CourierServiceType, BigDecimal> getAllRates(AddressModel sourceAddress, AddressModel destinationAddress, PackageDimensionModel dimension) throws DependentServiceException {
        Map<CourierServiceType, BigDecimal> rateMap = new TreeMap<>();

        for (CourierType type : CourierType.ENABLED) {
            RateScriptGenerator generator = new RateScriptGenerator(type);
            generator.withSourceAddress(sourceAddress);
            generator.withDestinationAddress(destinationAddress);
            generator.withDimensions(dimension);
            String script = generator.generate();
            JsonNode tree = engine.executeScriptToTree(script);
            System.out.println(tree.textValue());
            List<CourierRateResponseModel> result = engine.executeScript(script, new TypeReference<List<CourierRateResponseModel>>() {
            });


            for (CourierRateResponseModel entry : result) {
                rateMap.put(CourierServiceType.getFromServiceCode(type, entry.getServiceCode()), new BigDecimal(entry.getRate()));
            }
        }

        return rateMap;
    }

    @Override
    public ShipmentTrackingResponseModel getTracking(CourierType type, String trackingNumber) throws DependentServiceException {
        TrackScriptGenerator generator = new TrackScriptGenerator(type);
        generator.withTrackingNumber(trackingNumber);
        String script = generator.generate();
        JsonNode result = engine.executeScriptToTree(script);

        return type.getTrackingResponseParser().parseResponse(result);
    }
}