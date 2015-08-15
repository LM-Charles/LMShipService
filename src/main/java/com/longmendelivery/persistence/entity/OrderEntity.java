package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by desmond on 04/06/15.
 */
@Entity
@Table(name = "ORDER_")
@Data
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
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<ShipmentEntity> shipments;
    @Column(name = "ESTIMATE_COST")
    private BigDecimal estimateCost;
    @Column(name = "FINAL_COST")
    private BigDecimal finalCost;
    @Column(name = "FROM_ADDRESS", nullable = false)
    private String fromAddress;
    @Column(name = "FROM_CITY", nullable = false)
    private String fromCity;
    @Column(name = "FROM_PROVINCE", nullable = false)
    private String fromProvince;
    @Column(name = "FROM_CODE", nullable = false)
    private String fromCode;
    @Column(name = "FROM_COUNTRY", nullable = false)
    private String fromCountry;
    @Column(name = "TO_ADDRESS", nullable = false)
    private String toAddress;
    @Column(name = "TO_CITY", nullable = false)
    private String toCity;
    @Column(name = "TO_PROVINCE", nullable = false)
    private String toProvince;
    @Column(name = "TO_CODE", nullable = false)
    private String toCode;
    @Column(name = "TO_COUNTRY", nullable = false)
    private String toCountry;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COURIER_SERVICE_ID", nullable = false)
    private CourierServiceEntity courierServiceId;
    @Column(name = "HANDLER")
    private String handler;

    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
    private List<OrderStatusHistoryEntity> orderStatus;
}
