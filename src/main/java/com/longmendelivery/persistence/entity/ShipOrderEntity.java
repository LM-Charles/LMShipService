package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.courier.CourierServiceType;
import com.longmendelivery.service.model.order.GoodCategoryType;
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
@Entity(name = "ShipOrder")
@Data
@EqualsAndHashCode(exclude = {"shipments", "orderStatus"})
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
    @OneToMany(mappedBy = "order")
    private Set<ShipmentEntity> shipments;
    private BigDecimal estimateCost;
    private BigDecimal finalCost;
    @ManyToOne
    private AddressEntity fromAddress;
    @ManyToOne
    private AddressEntity toAddress;
    private CourierServiceType courierServiceType;
    private String handler;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderStatusHistoryEntity> orderStatus;
    private GoodCategoryType goodCategoryType;

    private BigDecimal declareValue;

    private BigDecimal insuranceValue;

}
