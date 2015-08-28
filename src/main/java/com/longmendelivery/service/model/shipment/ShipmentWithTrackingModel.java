package com.longmendelivery.service.model.shipment;

import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by desmond on 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentWithTrackingModel implements DTOModel {
    private Integer id;

    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;

    private String trackingNumber;
    private String trackingDocumentType;

    private ShipmentPackageType shipmentPackageType;

    private ShipmentTrackingModel tracking;
}
