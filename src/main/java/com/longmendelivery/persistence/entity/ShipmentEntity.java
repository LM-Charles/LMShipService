package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by desmond on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SHIPMENT")
public class ShipmentEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;
    @Column(name = "HEIGHT", nullable = false)
    private Integer height;
    @Column(name = "WIDTH", nullable = false)
    private Integer width;
    @Column(name = "LENGTH", nullable = false)
    private Integer length;
    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;
    @Column(name = "TRACKING_NUMBER")
    private String trackingNumber;

    @Column(name = "NICKNAME")
    private String nickName;
}
