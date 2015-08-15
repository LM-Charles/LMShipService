package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;

/**
 * Created by  rabiddesireon 15/08/15.
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
    public ShipmentTrackingResponseModel parseResponse(JsonNode jsonNode) throws DependentServiceException {
        String status = jsonNode.path("$.TrackReply.Notifications.Severity").asText();
        if (!status.equals("SUCCESS")) throw new DependentServiceException();
        DateTime pickUpDate = DateTime.parse(jsonNode.path("$.TrackReply.TrackDetails.ShipTimestamp").asText());
        DateTime trackingDate = DateTime.parse(jsonNode.path("$.TrackReply.TrackDetails.Events[0].Timestamp").asText());
        String trackingLocation = jsonNode.path("$.TrackReply.TrackDetails.Events[0].Address").asText();
        String trackingStatus = jsonNode.path("$.TrackReply.TrackDetails.Events[0].EventDescription").asText();
        ShipmentTrackingResponseModel model = new ShipmentTrackingResponseModel(pickUpDate, trackingDate, trackingLocation, trackingStatus);
        return model;
    }
}
