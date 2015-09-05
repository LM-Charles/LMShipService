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
import com.longmendelivery.service.model.shipment.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by desmond on 20/06/15.
 */
public class RSIShipmentClient implements ShipmentClient {

    public static final int TIMEOUT = 20;

    @AllArgsConstructor
    private final class GetRateForCourierTask implements Runnable {
        private AddressModel sourceAddress;
        private AddressModel destinationAddress;
        private ShipmentModel shipmentModel;
        private ConcurrentMap<CourierServiceType, BigDecimal> rateMap;
        private CourierType type;

        @Override
        public void run() {
            getRateForCourier();
        }

        private void getRateForCourier() {
            RateScriptGenerator generator = new RateScriptGenerator(type);
            generator.withSourceAddress(sourceAddress);
            generator.withDestinationAddress(destinationAddress);
            generator.withDimensions(new DimensionModel(shipmentModel.getLength(), shipmentModel.getWidth(), shipmentModel.getHeight(), shipmentModel.getWeight()));
            String script = generator.generate();
            try {
                List<RSIRateEntry> result = engine.executeScript(script, new TypeReference<List<RSIRateEntry>>() {
                });
                for (RSIRateEntry entry : result) {
                    rateMap.put(CourierServiceType.getFromServiceCode(type, entry.getServiceCode()), new BigDecimal(entry.getRate()));
                }
            } catch (DependentServiceException e) {
                System.out.println("Invalid query for shipment " + shipmentModel.toString());
            }
        }
    }

    private final RSIScriptEngine engine;
    private static final int MAX_THREAD_POOL = 16;

    public RSIShipmentClient() throws DependentServiceException {
        this.engine = new RSIScriptEngine();
    }

    @Override
    public Map<CourierServiceType, BigDecimal> getAllRates(AddressModel sourceAddress, AddressModel destinationAddress, ShipmentModel shipmentModel) throws DependentServiceException {
        ConcurrentHashMap<CourierServiceType, BigDecimal> targetRateMap = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(CourierType.ENABLED.size(), MAX_THREAD_POOL));

        for (CourierType type : CourierType.ENABLED) {
            executor.submit(new GetRateForCourierTask(sourceAddress, destinationAddress, shipmentModel, targetRateMap, type));
        }
        executor.shutdown();

        try {
            boolean isExecutorTimedOut = executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
            if (isExecutorTimedOut) {
                System.out.println("WARN GetAllRate timed out for some of the couriers.");
            }
        } catch (InterruptedException e) {
            System.out.println("WARN GetAllRate is interrupted for some of the couriers.");
        }

        return targetRateMap;
    }

    @Override
    public ShipmentTrackingModel getTracking(CourierType type, String trackingNumber) throws DependentServiceException, ResourceNotFoundException {
        TrackScriptGenerator generator = new TrackScriptGenerator(type);
        generator.withTrackingNumber(trackingNumber);
        String script = generator.generate();
        try {
            JsonNode result = engine.executeScriptToTree(script);
            ShipmentTrackingModel responseModel = type.getTrackingResponseParser().parseResponse(result);
            return responseModel;
        } catch (DependentServiceException e) {
            System.out.println("Invalid query for tracking " + type.toString() + " with " + trackingNumber);
            return null;
        }
    }
}
