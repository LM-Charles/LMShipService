package com.longmendelivery.persistence.model;

import org.joda.time.DateTime;
import java.math.BigDecimal;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class Shipment {
    AppUser client;
    DateTime orderDate;

    Integer height;
    Integer width;
    Integer length;

    Integer weight;

    BigDecimal estimateCost;
    BigDecimal finalCost;

    String fromAddress;
    String fromCity;
    String fromProvince;
    String fromCode;
    String fromCountry;

    String toAddress;
    String toCity;
    String toProvince;
    String toCode;
    String toCountry;

    ShipmentService shipmentService;
    String trackingNumber;
    String trackingDocumentType;

    String handler;
}
