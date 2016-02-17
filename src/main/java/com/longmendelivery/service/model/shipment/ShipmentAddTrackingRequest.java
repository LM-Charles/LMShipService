package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rabiddesire on 23/08/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentAddTrackingRequest {
    private String trackingNumber;
    private String trackingDocumentType;
}
