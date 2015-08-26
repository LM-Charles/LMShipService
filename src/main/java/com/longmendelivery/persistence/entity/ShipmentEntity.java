package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.order.GoodCategoryType;
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
@Entity(name = "Shipment")
@Table(name = "Shipment", indexes = {@Index(name = "Shipment_order", columnList = "order_id")})
public class ShipmentEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ShipOrderEntity order;
    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
    private String trackingNumber;
    private String nickName;
    private GoodCategoryType goodCategoryType;

}
