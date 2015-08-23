package com.longmendelivery.service.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.courier.CourierServiceType;
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
public class ShipOrderModel implements DTOModel {
    @JsonProperty
    @NonNull
    private Integer id;
    @JsonProperty
    @NonNull
    private Integer client;
    @JsonProperty
    @NonNull
    private DateTime orderDate;
    @JsonProperty
    @NonNull
    private CourierServiceType courierServiceType;
    @JsonProperty
    @NonNull
    private Set<Integer> shipments;
    @JsonProperty
    @NonNull
    private BigDecimal estimateCost;
    @JsonProperty
    @NonNull
    private BigDecimal finalCost;
    @JsonProperty
    @NonNull
    private AddressModel fromAddress;
    @JsonProperty
    @NonNull
    private AddressModel toAddress;
    @JsonProperty
    private String handler;
    @JsonProperty
    private GoodCategoryType goodCategoryType;
    @JsonProperty
    private BigDecimal declareValue;
    @JsonProperty
    private BigDecimal insuranceValue;
}