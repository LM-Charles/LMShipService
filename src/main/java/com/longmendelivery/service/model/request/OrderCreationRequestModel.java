package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.courier.CourierServiceType;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.GoodCategoryType;
import com.longmendelivery.service.model.order.ShipmentModel;
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
public class OrderCreationRequestModel implements DTOModel {
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
    private GoodCategoryType goodCategoryType;
    @JsonProperty
    private BigDecimal declareValue;
    @JsonProperty
    private BigDecimal insuranceValue;

}
