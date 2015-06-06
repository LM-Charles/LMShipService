package com.longmendelivery.persistence.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "SHIPMENT")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private AppUser clientId;

    @Column(name = "ORDER_DATE", nullable = false)
    private DateTime orderDate;

    @Column(name = "HEIGHT", nullable = false)
    private Integer height;
    @Column(name = "WIDTH", nullable = false)
    private Integer width;
    @Column(name = "LENGTH", nullable = false)
    private Integer length;

    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;

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

    @ManyToMany
    @JoinColumn(name = "SHIPMENT_SERVICE_ID")
    private ShipmentService shipmentServiceId;

    @Column(name = "TRACKING_NUMBER")
    private String trackingNumber;
    @Column(name = "TRACKING_DOCUMENT_TYPE")
    private String trackingDocumentType;

    @Column(name = "HANDLER")
    private String handler;

    public AppUser getClientId() {
        return clientId;
    }

    public void setClientId(AppUser clientId) {
        this.clientId = clientId;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
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

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }

    public BigDecimal getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(BigDecimal finalCost) {
        this.finalCost = finalCost;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromProvince() {
        return fromProvince;
    }

    public void setFromProvince(String fromProvince) {
        this.fromProvince = fromProvince;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToProvince() {
        return toProvince;
    }

    public void setToProvince(String toProvince) {
        this.toProvince = toProvince;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public ShipmentService getShipmentServiceId() {
        return shipmentServiceId;
    }

    public Shipment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        if (id != null ? !id.equals(shipment.id) : shipment.id != null) return false;
        if (clientId != null ? !clientId.equals(shipment.clientId) : shipment.clientId != null) return false;
        if (orderDate != null ? !orderDate.equals(shipment.orderDate) : shipment.orderDate != null) return false;
        if (height != null ? !height.equals(shipment.height) : shipment.height != null) return false;
        if (width != null ? !width.equals(shipment.width) : shipment.width != null) return false;
        if (length != null ? !length.equals(shipment.length) : shipment.length != null) return false;
        if (weight != null ? !weight.equals(shipment.weight) : shipment.weight != null) return false;
        if (estimateCost != null ? !estimateCost.equals(shipment.estimateCost) : shipment.estimateCost != null)
            return false;
        if (finalCost != null ? !finalCost.equals(shipment.finalCost) : shipment.finalCost != null) return false;
        if (fromAddress != null ? !fromAddress.equals(shipment.fromAddress) : shipment.fromAddress != null)
            return false;
        if (fromCity != null ? !fromCity.equals(shipment.fromCity) : shipment.fromCity != null) return false;
        if (fromProvince != null ? !fromProvince.equals(shipment.fromProvince) : shipment.fromProvince != null)
            return false;
        if (fromCode != null ? !fromCode.equals(shipment.fromCode) : shipment.fromCode != null) return false;
        if (fromCountry != null ? !fromCountry.equals(shipment.fromCountry) : shipment.fromCountry != null)
            return false;
        if (toAddress != null ? !toAddress.equals(shipment.toAddress) : shipment.toAddress != null) return false;
        if (toCity != null ? !toCity.equals(shipment.toCity) : shipment.toCity != null) return false;
        if (toProvince != null ? !toProvince.equals(shipment.toProvince) : shipment.toProvince != null) return false;
        if (toCode != null ? !toCode.equals(shipment.toCode) : shipment.toCode != null) return false;
        if (toCountry != null ? !toCountry.equals(shipment.toCountry) : shipment.toCountry != null) return false;
        if (shipmentServiceId != null ? !shipmentServiceId.equals(shipment.shipmentServiceId) : shipment.shipmentServiceId != null)
            return false;
        if (trackingNumber != null ? !trackingNumber.equals(shipment.trackingNumber) : shipment.trackingNumber != null)
            return false;
        if (trackingDocumentType != null ? !trackingDocumentType.equals(shipment.trackingDocumentType) : shipment.trackingDocumentType != null)
            return false;
        return !(handler != null ? !handler.equals(shipment.handler) : shipment.handler != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (estimateCost != null ? estimateCost.hashCode() : 0);
        result = 31 * result + (finalCost != null ? finalCost.hashCode() : 0);
        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (fromCity != null ? fromCity.hashCode() : 0);
        result = 31 * result + (fromProvince != null ? fromProvince.hashCode() : 0);
        result = 31 * result + (fromCode != null ? fromCode.hashCode() : 0);
        result = 31 * result + (fromCountry != null ? fromCountry.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        result = 31 * result + (toCity != null ? toCity.hashCode() : 0);
        result = 31 * result + (toProvince != null ? toProvince.hashCode() : 0);
        result = 31 * result + (toCode != null ? toCode.hashCode() : 0);
        result = 31 * result + (toCountry != null ? toCountry.hashCode() : 0);
        result = 31 * result + (shipmentServiceId != null ? shipmentServiceId.hashCode() : 0);
        result = 31 * result + (trackingNumber != null ? trackingNumber.hashCode() : 0);
        result = 31 * result + (trackingDocumentType != null ? trackingDocumentType.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    public Shipment(AppUser clientId, DateTime orderDate, Integer height, Integer width, Integer length, Integer weight, BigDecimal estimateCost, BigDecimal finalCost, String fromAddress, String fromCity, String fromProvince, String fromCode, String fromCountry, String toAddress, String toCity, String toProvince, String toCode, String toCountry, ShipmentService shipmentServiceId, String trackingNumber, String trackingDocumentType, String handler) {
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.estimateCost = estimateCost;
        this.finalCost = finalCost;
        this.fromAddress = fromAddress;
        this.fromCity = fromCity;

        this.fromProvince = fromProvince;

        this.fromCode = fromCode;
        this.fromCountry = fromCountry;
        this.toAddress = toAddress;
        this.toCity = toCity;
        this.toProvince = toProvince;
        this.toCode = toCode;
        this.toCountry = toCountry;
        this.shipmentServiceId = shipmentServiceId;
        this.trackingNumber = trackingNumber;
        this.trackingDocumentType = trackingDocumentType;
        this.handler = handler;
    }

    public void setShipmentServiceId(ShipmentService shipmentServiceId) {
        this.shipmentServiceId = shipmentServiceId;
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

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
