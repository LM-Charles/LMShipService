package com.longmendelivery.service.model.order;

import com.longmendelivery.persistence.entity.AppointmentSlotType;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.ShipmentWithTrackingModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipOrderWithStatusModel implements DTOModel {
    @NonNull
    private Integer id;
    @NonNull
    private Integer client;
    @NonNull
    private DateTime orderDate;
    @NonNull
    private CourierServiceType courierServiceType;
    private String service_icon_url;
    @NonNull
    private List<ShipmentWithTrackingModel> shipments;
    private BigDecimal estimateCost;
    private BigDecimal finalCost;
    @NonNull
    private AddressModel fromAddress;
    @NonNull
    private AddressModel toAddress;
    private String handler;
    private BigDecimal declareValue;
    private BigDecimal insuranceValue;
    private DateTime appointmentDate;
    private AppointmentSlotType appointmentSlotType;
    private OrderStatusModel orderStatusModel;
    private List<OrderStatusModel> orderStatusHistory;
}
