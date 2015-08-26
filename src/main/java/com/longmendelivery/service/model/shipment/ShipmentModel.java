package com.longmendelivery.service.model.shipment;

import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.DimensionModel;
import com.longmendelivery.service.model.order.GoodCategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by desmond on 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentModel extends DimensionModel implements DTOModel {
    private Integer id;

    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;

    private String trackingNumber;
    private String trackingDocumentType;
    private GoodCategoryType goodCategoryType;


    private String nickName;
    private ShipmentPackageType shipmentPackageType;
}
