package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import com.longmendelivery.persistence.entity.GoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel implements Model {
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
    private GoodCategory goodCategory;
    @JsonProperty
    private BigDecimal declareValue;
    @JsonProperty
    private BigDecimal insuranceValue;
}
