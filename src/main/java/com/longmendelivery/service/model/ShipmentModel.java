package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
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
public class ShipmentModel implements Model {
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
}
