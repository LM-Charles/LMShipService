package com.longmendelivery.persistence.entity;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "ORDER")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private AppUserEntity client;

    @Column(name = "ORDER_DATE", nullable = false)
    private DateTime orderDate;
    @OneToMany(mappedBy = "order")
    private Set<ShipmentEntity> shipments;
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
    @ManyToOne
    @JoinColumn(name = "COURIER_SERVICE_ID", nullable = false)
    private CourierServiceEntity courierServiceId;
    @Column(name = "HANDLER")
    private String handler;

    public OrderEntity() {
    }

    public OrderEntity(AppUserEntity client, DateTime orderDate, Set<ShipmentEntity> shipments, BigDecimal estimateCost, BigDecimal finalCost, String fromAddress, String fromCity, String fromProvince, String fromCode, String fromCountry, String toAddress, String toCity, String toProvince, String toCode, String toCountry, CourierServiceEntity courierServiceId, String handler) {

        this.client = client;
        this.orderDate = orderDate;
        this.shipments = shipments;
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
        this.courierServiceId = courierServiceId;
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", clientId=" + client +
                ", orderDate=" + orderDate +
                ", shipments=" + shipments +
                ", estimateCost=" + estimateCost +
                ", finalCost=" + finalCost +
                ", fromAddress='" + fromAddress + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", fromProvince='" + fromProvince + '\'' +
                ", fromCode='" + fromCode + '\'' +
                ", fromCountry='" + fromCountry + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", toCity='" + toCity + '\'' +
                ", toProvince='" + toProvince + '\'' +
                ", toCode='" + toCode + '\'' +
                ", toCountry='" + toCountry + '\'' +
                ", courierServiceId=" + courierServiceId +
                ", handler='" + handler + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity order = (OrderEntity) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (shipments != null ? !shipments.equals(order.shipments) : order.shipments != null) return false;
        if (estimateCost != null ? !estimateCost.equals(order.estimateCost) : order.estimateCost != null) return false;
        if (finalCost != null ? !finalCost.equals(order.finalCost) : order.finalCost != null) return false;
        if (fromAddress != null ? !fromAddress.equals(order.fromAddress) : order.fromAddress != null) return false;
        if (fromCity != null ? !fromCity.equals(order.fromCity) : order.fromCity != null) return false;
        if (fromProvince != null ? !fromProvince.equals(order.fromProvince) : order.fromProvince != null) return false;
        if (fromCode != null ? !fromCode.equals(order.fromCode) : order.fromCode != null) return false;
        if (fromCountry != null ? !fromCountry.equals(order.fromCountry) : order.fromCountry != null) return false;
        if (toAddress != null ? !toAddress.equals(order.toAddress) : order.toAddress != null) return false;
        if (toCity != null ? !toCity.equals(order.toCity) : order.toCity != null) return false;
        if (toProvince != null ? !toProvince.equals(order.toProvince) : order.toProvince != null) return false;
        if (toCode != null ? !toCode.equals(order.toCode) : order.toCode != null) return false;
        if (toCountry != null ? !toCountry.equals(order.toCountry) : order.toCountry != null) return false;
        if (courierServiceId != null ? !courierServiceId.equals(order.courierServiceId) : order.courierServiceId != null)
            return false;
        return !(handler != null ? !handler.equals(order.handler) : order.handler != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (shipments != null ? shipments.hashCode() : 0);
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
        result = 31 * result + (courierServiceId != null ? courierServiceId.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUserEntity getClient() {
        return client;
    }

    public void setClient(AppUserEntity clientId) {
        this.client = clientId;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Set<ShipmentEntity> getShipments() {
        return shipments;
    }

    public void setShipments(Set<ShipmentEntity> shipments) {
        this.shipments = shipments;
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

    public CourierServiceEntity getCourierServiceId() {
        return courierServiceId;
    }

    public void setCourierServiceId(CourierServiceEntity courierServiceId) {
        this.courierServiceId = courierServiceId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
