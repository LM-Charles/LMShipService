package com.longmendelivery.service.model.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.ShipmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateRequest implements DTOModel {
    @JsonProperty
    private AddressModel toAddress;
    @JsonProperty
    private AddressModel fromAddress;
    @JsonProperty
    private List<ShipmentModel> shipments;
}
