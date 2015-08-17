package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by desmond on 04/06/15.
 */
@Entity
@Table(name = "ORDER_STATUS_HISTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryEntity implements DAOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "STATUS", nullable = false)
    String status;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    OrderEntity orderId;

    @Column(name = "STATUS_DESCRIPTION", nullable = false)
    String statusDescription;

    @Column(name = "HANDLER")
    Integer handler;

    @Column(name = "STATUS_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    DateTime statusDate;
}
