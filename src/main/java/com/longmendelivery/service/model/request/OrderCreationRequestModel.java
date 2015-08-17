package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import com.longmendelivery.persistence.entity.GoodCategory;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.CourierServiceType;
import com.longmendelivery.service.model.ShipmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by desmond on 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequestModel implements Model {
    @JsonProperty
    @NonNull
    private Integer userId;

    @JsonProperty
    @NonNull
    private DateTime orderDate;

    @JsonProperty
    @NonNull
    private AddressModel toAddress;

    @JsonProperty
    @NonNull
    private AddressModel fromAddress;

    @JsonProperty
    @NonNull
    private CourierServiceType courierServiceType;

    @JsonProperty
    @NonNull
    private Set<ShipmentModel> shipments;

    @JsonProperty
    private GoodCategory goodCategory;
    @JsonProperty
    private BigDecimal declareValue;
    @JsonProperty
    private BigDecimal insuranceValue;

}
