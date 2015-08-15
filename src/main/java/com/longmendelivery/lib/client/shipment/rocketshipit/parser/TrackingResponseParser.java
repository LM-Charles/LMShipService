package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;

/**
 * Created by  rabiddesireon 15/08/15.
 */
public interface TrackingResponseParser {
    ShipmentTrackingResponseModel parseResponse(JsonNode jsonNode) throws DependentServiceException;
}