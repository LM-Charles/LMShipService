package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.ShipmentModel;
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
public class RateRequestModel implements Model {
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
