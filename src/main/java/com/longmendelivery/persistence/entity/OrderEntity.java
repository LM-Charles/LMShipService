package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import com.longmendelivery.service.model.CourierServiceType;
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
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "ORDER_")
@Data
@EqualsAndHashCode(exclude = {"shipments", "orderStatus"})
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private AppUserEntity client;

    @Column(name = "ORDER_DATE", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime orderDate;
    @OneToMany(mappedBy = "order")
    private Set<ShipmentEntity> shipments;
    @Column(name = "ESTIMATE_COST")
    private BigDecimal estimateCost;
    @Column(name = "FINAL_COST")
    private BigDecimal finalCost;
    @ManyToOne
    @JoinColumn(name = "FROM_ADDRESS_ID")
    private AddressEntity fromAddress;
    @ManyToOne
    @JoinColumn(name = "TO_ADDRESS_ID")
    private AddressEntity toAddress;
    @Column(name = "COURIER_SERVICE", nullable = false)
    private CourierServiceType courierServiceType;
    @Column(name = "HANDLER")
    private String handler;
    @OneToMany(mappedBy = "orderId", fetch = FetchType.LAZY)
    private List<OrderStatusHistoryEntity> orderStatus;
    @Column(name = "GOOD_CATEGORY")
    private GoodCategory goodCategory;

    @Column(name = "DECLARE_VALUE")
    private BigDecimal declareValue;

    @Column(name = "INSURANCE_VALUE")
    private BigDecimal insuranceValue;

}
