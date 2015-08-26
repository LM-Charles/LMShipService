package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by desmond on 15/08/15.
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
    public ShipmentTrackingModel parseResponse(JsonNode jsonNode) throws DependentServiceException {
        String status = jsonNode.at("/TrackResponse/Response/ResponseStatusDescription").asText();
        if (!status.equals("Success"))
            throw new DependentServiceException("UPS Responded with failure message to this tracking request");
        DateTimeFormatter upsDateTimeFormat = DateTimeFormat.forPattern("yyyyMMDD");
        String pickupDateString = jsonNode.at("/TrackResponse/Shipment/PickupDate").asText();
        DateTime pickUpDate = pickupDateString.isEmpty() ? null : DateTime.parse(pickupDateString, upsDateTimeFormat);
        String trackingDateString = jsonNode.at("/TrackResponse/Shipment/Package/Activity/0/Date").asText();
        DateTime trackingDate = trackingDateString.isEmpty() ? null : DateTime.parse(trackingDateString, upsDateTimeFormat);
        String trackingCity = jsonNode.at("/TrackResponse/Shipment/Package/Activity/0/ActivityLocation/Address/City").asText();
        String trackingCountry = jsonNode.at("/TrackResponse/Shipment/Package/Activity/0/ActivityLocation/Address/CountryCode").asText();

        String trackingStatus = jsonNode.at("/TrackResponse/Shipment/Package/Activity/0/Status/StatusType/Description").asText();
        ShipmentTrackingModel model = new ShipmentTrackingModel(pickUpDate, trackingDate, trackingCity, trackingCountry, trackingStatus);
        return model;
    }
}
