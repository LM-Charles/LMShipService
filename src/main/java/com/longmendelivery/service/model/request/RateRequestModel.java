package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.ShipmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateRequestModel implements DTOModel {
    @JsonProperty
    @NonNull
    private AddressModel toAddress;

    @JsonProperty
    @NonNull
    private AddressModel fromAddress;

    @JsonProperty
    @NonNull
    private List<ShipmentModel> shipments;
}
