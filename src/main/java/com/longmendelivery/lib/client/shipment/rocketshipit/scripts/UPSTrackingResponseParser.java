package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;

/**
 * Created by  rabiddesireon 15/08/15.
 */
public class UPSTrackingResponseParser implements TrackingResponseParser {
    //    $.TrackResponse.Response.ResponseStatusDescription : '0' => "Success"
//    $.TrackResponse.Shipment.PickupDate : '0' => "20100608"
//    $.TrackResponse.Shipment.Package.Activity[0].Status.StatusType.Description :  '0' => "DELIVERED"
//    $.TrackResponse.Shipment.Package.Activity[0].ActivityLocation.Address :
//            '0' ...
//            'City' => "ANYTOWN"
//            'StateProvinceCode' => "GA"
//            'PostalCode' => "30340"
//            'CountryCode' => "US"
    @Override
    public ShipmentTrackingResponseModel parseResponse(JsonNode jsonNode) throws DependentServiceException {
        String status = jsonNode.path(" $.TrackResponse.Response.ResponseStatusDescription").asText();
        if (!status.equals("Success")) throw new DependentServiceException();
        DateTime pickUpDate = DateTime.parse(jsonNode.path("$.TrackResponse.Shipment.PickupDate").asText());
        DateTime trackingDate = DateTime.parse(jsonNode.path("$.TrackReply.TrackDetails.Events[0].Timestamp").asText());
        String trackingLocation = jsonNode.path("$.TrackResponse.Shipment.Package.Activity[0].ActivityLocation.Address").asText();
        String trackingStatus = jsonNode.path(".TrackResponse.Shipment.Package.Activity[0].Status.StatusType.Description").asText();
        ShipmentTrackingResponseModel model = new ShipmentTrackingResponseModel(pickUpDate, trackingDate, trackingLocation, trackingStatus);
        return model;
    }
}
