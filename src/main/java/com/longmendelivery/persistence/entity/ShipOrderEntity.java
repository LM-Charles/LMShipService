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
import java.util.List;
import java.util.Set;

/**
 * Created by rabiddesire on 04/06/15.
 */
@Entity(name = "ShipOrder")
@Data
@EqualsAndHashCode(exclude = {"shipments", "orderStatuses"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShipOrder", indexes = {@Index(name = "ShipOrder_client", columnList = "client_id"), @Index(name = "ShipOrder_orderDate", columnList = "orderDate")})

public class ShipOrderEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private AppUserEntity client;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime orderDate;
    @Enumerated(EnumType.STRING)
    private CourierServiceType courierServiceType;
    @OneToMany(mappedBy = "order")
    private List<ShipmentEntity> shipments;
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
    @Enumerated(EnumType.STRING)
    private AppointmentSlotType appointmentSlotType;
    private String nickname;

}
