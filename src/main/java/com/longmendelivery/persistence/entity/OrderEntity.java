package com.longmendelivery.persistence.entity;

import com.longmendelivery.lib.conversion.DAOEntity;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@Entity
@Table(name = "ORDER_")
public class OrderEntity implements DAOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private AppUserEntity client;

    @Column(name = "ORDER_DATE", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime orderDate;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COURIER_SERVICE_ID", nullable = false)
    private CourierServiceEntity courierServiceId;
    @Column(name = "HANDLER")
    private String handler;

    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
    private Set<OrderStatusHistoryEntity> orderStatus;


    public OrderEntity() {
    }

    public OrderEntity(AppUserEntity client, DateTime orderDate, Set<ShipmentEntity> shipments, BigDecimal estimateCost, BigDecimal finalCost, String fromAddress, String fromCity, String fromProvince, String fromCode, String fromCountry, String toAddress, String toCity, String toProvince, String toCode, String toCountry, CourierServiceEntity courierServiceId, String handler, Set<OrderStatusHistoryEntity> orderStatus) {

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
        this.orderStatus = orderStatus;
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

    public Set<OrderStatusHistoryEntity> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Set<OrderStatusHistoryEntity> orderStatus) {
        this.orderStatus = orderStatus;
    }
}
