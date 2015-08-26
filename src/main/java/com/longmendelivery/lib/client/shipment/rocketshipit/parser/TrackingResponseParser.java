package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;

/**
 * Created by desmond on 15/08/15.
 */
public interface TrackingResponseParser {
    ShipmentTrackingModel parseResponse(JsonNode jsonNode) throws DependentServiceException;
}