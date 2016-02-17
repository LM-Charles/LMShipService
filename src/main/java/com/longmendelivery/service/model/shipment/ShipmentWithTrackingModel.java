package com.longmendelivery.service.model.shipment;

import com.longmendelivery.persistence.entity.LengthUnit;
import com.longmendelivery.persistence.entity.WeightUnit;
import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rabiddesire on 20/06/15.
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
    //There two are for display preference only, all measurements are standardized using the metric system in the system.
    private LengthUnit displayLengthPreference;
    private WeightUnit displayWeightPreference;
}
