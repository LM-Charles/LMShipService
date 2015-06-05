package com.longmendelivery.persistence.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class ShipmentStatusHistory {
    String id;
    Shipment shipment;
    String status;
    String statusDescription;
    String handler;
}
