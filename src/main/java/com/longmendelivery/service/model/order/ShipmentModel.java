package com.longmendelivery.service.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentModel implements DTOModel {
    @JsonProperty
    @NonNull
    private Integer height;
    @JsonProperty
    @NonNull
    private Integer width;
    @JsonProperty
    @NonNull
    private Integer length;
    @JsonProperty
    @NonNull
    private Integer weight;

    @JsonProperty
    private String trackingNumber;
    @JsonProperty
    private String trackingDocumentType;

    @JsonProperty
    private String nickName;

    private ShipmentPackageType shipmentPackageType;
}
