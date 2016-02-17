package com.longmendelivery.lib.client.shipment.rocketshipit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.model.shipment.ShipmentTrackingModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by rabiddesire on 15/08/15.
 */
public class CanadaTrackingResponseParser implements TrackingResponseParser {
    //    /tracking-detail/xmlns => "http://www.canadapost.ca/ws/track"
//    /tracking-detail/significant-events/occurrence[0]/event-date => "2013-01-13"
//    /tracking-detail/significant-events/occurrence[0]/event-description => "Item successfully delivered"
//    /tracking-detail/significant-events/occurrence[0]/event-site
//            'event-site' => "DARTMOUTH"
//            'event-province' => "NS"
    @Override
    public ShipmentTrackingModel parseResponse(JsonNode jsonNode, String trackingNumber) throws DependentServiceException {
        String status = jsonNode.at("/tracking-detail/xmlns").asText();
        if (!status.equals("http://www.canadapost.ca/ws/track")) {
            System.out.println(jsonNode.toString());
            throw new DependentServiceException("UPS Responded with failure message to this tracking request");
        }
        DateTimeFormatter canadaDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-DD");
        Integer numberOfEvent = jsonNode.at("/tracking-detail/significant-events/occurrence").size();
        String pickUpDateString = jsonNode.at("/tracking-detail/significant-events/occurrence/" + (numberOfEvent - 1) + "/event-date").asText();
        DateTime pickUpDate = pickUpDateString.isEmpty() ? null : DateTime.parse(pickUpDateString, canadaDateTimeFormat);
        String trackingDateString = jsonNode.at("/tracking-detail/significant-events/occurrence/0/event-date").asText();
        DateTime trackingDate = trackingDateString.isEmpty() ? null : DateTime.parse(trackingDateString, canadaDateTimeFormat);
        String trackingCity = jsonNode.at("/tracking-detail/significant-events/occurrence/0/event-site").asText();
        String trackingCountry = null;

        String trackingStatus = jsonNode.at("/tracking-detail/significant-events/occurrence/0/event-description").asText();
        ShipmentTrackingModel model = new ShipmentTrackingModel(pickUpDate, trackingDate, trackingCity, trackingCountry, trackingStatus, buildTrackingURL(trackingNumber));
        return model;
    }

    public String buildTrackingURL(String trackingNumber) {
        return "http://www.canadapost.ca/cpotools/apps/track/personal/findByTrackNumber?trackingNumber=" + trackingNumber + "";
    }
}
