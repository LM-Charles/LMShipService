package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity(name = "OrderStatusHistoryEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String status;

    @ManyToOne
    ShipOrderEntity order;

    String statusDescription;

    Integer handler;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime statusDate;
}
