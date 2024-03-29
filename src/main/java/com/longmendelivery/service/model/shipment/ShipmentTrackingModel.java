package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by rabiddesire on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentTrackingModel {
    DateTime pickUpDate;
    DateTime trackingDate;
    String trackingCity;
    String trackingCountry;
    String trackingStatus;
    String trackingURL;
}