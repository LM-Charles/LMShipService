package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.shipment.ShipmentTrackingResponse;

/**
 * Created by desmond on 15/08/15.
 */
public interface TrackingResponseParser {
    ShipmentTrackingResponse parseResponse(JsonNode jsonNode) throws DependentServiceException;
}