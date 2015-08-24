package com.longmendelivery.service.model.order;

import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
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
public class OrderCreationRequest implements DTOModel {
    @NonNull
    private Integer client;
    @NonNull
    private DateTime orderDate;
    @NonNull
    private CourierServiceType courierServiceType;
    @NonNull
    private Set<ShipmentModel> shipments;
    @NonNull
    private AddressModel fromAddress;
    @NonNull
    private AddressModel toAddress;
    private String handler;
    private GoodCategoryType goodCategoryType;
    private BigDecimal declareValue;
    private BigDecimal insuranceValue;
}