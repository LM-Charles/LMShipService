package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;
import org.joda.time.DateTime;

/**
 * Created by desmond on 15/08/15.
 */
public class FedexTrackingResponseParser implements TrackingResponseParser {
    //    $.TrackReply.Notifications.Severity :	'0' => "SUCCESS"
    //    $.TrackReply.TrackDetails.ShipTimestamp ：     '0' => "2015-07-03T00:00:00"
    //    $.TrackReply.TrackDetails.Events[0].EventDescription：     '0' => "Delivered"
    //    $.TrackReply.TrackDetails.Events[0].Address
    //    '0' ...
    //            'City' => "BEIJING BEIJING"
    //            'PostalCode' => "101407"
    //            'CountryCode' => "CN"
    //            'Residential' => "false"
    @Override
    public ShipmentTrackingModel parseResponse(JsonNode jsonNode) throws DependentServiceException {
        System.out.println(jsonNode.toString());
        String status = jsonNode.at("/TrackReply/Notifications/Severity").asText();
        if (!status.equals("SUCCESS"))
            throw new DependentServiceException("Tracking request did not return success for Fedex");
        DateTime pickUpDate = DateTime.parse(jsonNode.at("/TrackReply/TrackDetails/ShipTimestamp").asText());
        DateTime trackingDate = DateTime.parse(jsonNode.at("/TrackReply/TrackDetails/Events/0/Timestamp").asText());
        String trackingCity = jsonNode.at("/TrackReply/TrackDetails/Events/0/Address/City").asText();
        String trackingCountry = jsonNode.at("/TrackReply/TrackDetails/Events/0/Address/CountryCode").asText();
        String trackingStatus = jsonNode.at("/TrackReply/TrackDetails/Events/0/EventDescription").asText();
        ShipmentTrackingModel model = new ShipmentTrackingModel(pickUpDate, trackingDate, trackingCity, trackingCountry, trackingStatus);
        return model;
    }
}
