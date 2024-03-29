package com.longmendelivery.persistence.entity;

import com.longmendelivery.persistence.DAOEntity;
import com.longmendelivery.service.model.order.GoodCategoryType;
import com.longmendelivery.service.model.shipment.ShipmentPackageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by rabiddesire on 04/06/15.
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
    @Enumerated(EnumType.STRING)
    private GoodCategoryType goodCategoryType;
    @Enumerated(EnumType.STRING)
    private ShipmentPackageType shipmentPackageType;
    //There two are for display preference only, all measurements are standardized using the metric system in the system.
    @Enumerated(EnumType.STRING)
    private LengthUnit displayLengthPreference;
    @Enumerated(EnumType.STRING)
    private WeightUnit displayWeightPreference;

}
