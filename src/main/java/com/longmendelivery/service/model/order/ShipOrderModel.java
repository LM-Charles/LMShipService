package com.longmendelivery.service.model.order;

import com.longmendelivery.persistence.entity.AppointmentSlotType;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.ShipmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipOrderModel implements DTOModel {
    private Integer id;
    private Integer client;
    private DateTime orderDate;
    private CourierServiceType courierServiceType;
    private List<ShipmentModel> shipments;
    private BigDecimal estimateCost;
    private BigDecimal finalCost;
    private AddressModel fromAddress;
    private AddressModel toAddress;
    private String handler;
    private BigDecimal declareValue;

    private BigDecimal insuranceValue;
    private DateTime appointmentDate;
    private AppointmentSlotType appointmentSlotType;
    private String nickname;

}
