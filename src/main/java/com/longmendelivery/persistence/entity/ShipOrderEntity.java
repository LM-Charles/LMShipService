package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by desmond on 04/06/15.
 */
@Entity(name = "ShipOrder")
@Data
@EqualsAndHashCode(exclude = {"shipments", "orderStatuses"})
@NoArgsConstructor
@AllArgsConstructor
public class ShipOrderEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private AppUserEntity client;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime orderDate;
    private CourierServiceType courierServiceType;
    @OneToMany(mappedBy = "order")
    private Set<ShipmentEntity> shipments;
    private BigDecimal estimateCost;
    private BigDecimal finalCost;
    @ManyToOne
    private AddressEntity fromAddress;
    @ManyToOne
    private AddressEntity toAddress;
    private String handler;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderStatusHistoryEntity> orderStatuses;
    private BigDecimal declareValue;
    private BigDecimal insuranceValue;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime appointmentDate;
    private AppointmentSlotType appointmentSlotType;
}
