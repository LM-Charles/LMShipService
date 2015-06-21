package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;

import javax.persistence.*;

/**
 * Created by desmond on 04/06/15.
 */
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
    @Column(name = "TRACKING_DOCUMENT_TYPE")
    private String trackingDocumentType;

    public ShipmentEntity(OrderEntity order, Integer height, Integer width, Integer length, Integer weight, String trackingNumber, String trackingDocumentType) {

        this.order = order;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.trackingNumber = trackingNumber;
        this.trackingDocumentType = trackingDocumentType;
    }

    public ShipmentEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentEntity shipment = (ShipmentEntity) o;

        if (id != null ? !id.equals(shipment.id) : shipment.id != null) return false;
        if (order != null ? !order.equals(shipment.order) : shipment.order != null) return false;
        if (height != null ? !height.equals(shipment.height) : shipment.height != null) return false;
        if (width != null ? !width.equals(shipment.width) : shipment.width != null) return false;
        if (length != null ? !length.equals(shipment.length) : shipment.length != null) return false;
        if (weight != null ? !weight.equals(shipment.weight) : shipment.weight != null) return false;
        if (trackingNumber != null ? !trackingNumber.equals(shipment.trackingNumber) : shipment.trackingNumber != null)
            return false;
        return !(trackingDocumentType != null ? !trackingDocumentType.equals(shipment.trackingDocumentType) : shipment.trackingDocumentType != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.getId().hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (trackingNumber != null ? trackingNumber.hashCode() : 0);
        result = 31 * result + (trackingDocumentType != null ? trackingDocumentType.hashCode() : 0);
        return result;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity orderId) {
        this.order = orderId;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingDocumentType() {
        return trackingDocumentType;
    }

    public void setTrackingDocumentType(String trackingDocumentType) {
        this.trackingDocumentType = trackingDocumentType;
    }

}
