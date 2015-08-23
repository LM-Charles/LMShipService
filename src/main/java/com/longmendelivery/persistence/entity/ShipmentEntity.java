package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Shipment")
public class ShipmentEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private OrderEntity order;
    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
    private String trackingNumber;
    private String nickName;
}
