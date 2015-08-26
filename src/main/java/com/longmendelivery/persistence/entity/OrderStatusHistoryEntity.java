package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.order.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity(name = "OrderStatusHistoryEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryEntity implements DAOEntity {
    public static OrderStatusHistoryEntity getMostRecentOrderStatusHistoryEntity(Set<OrderStatusHistoryEntity> entities) {
        OrderStatusHistoryEntity[] entitiesArray = entities.toArray(new OrderStatusHistoryEntity[entities.size()]);
        Arrays.sort(entitiesArray, new Comparator<OrderStatusHistoryEntity>() {
            @Override
            public int compare(OrderStatusHistoryEntity entity, OrderStatusHistoryEntity other) {
                return entity.getStatusDate().compareTo(other.getStatusDate());
            }
        });
        return entitiesArray[0];
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(value = EnumType.STRING)
    OrderStatusType status;

    @ManyToOne
    ShipOrderEntity order;

    String statusDescription;

    String handler;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime statusDate;
}
