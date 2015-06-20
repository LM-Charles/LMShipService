package com.longmendelivery.app.model;

import org.joda.time.DateTime;

import java.util.Set;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class OrderCreationRequestModel {
    private Integer userId;
    private DateTime orderDate;
    private String fromAddress;
    private String fromCity;
    private String fromProvince;
    private String fromCode;
    private String fromCountry;
    private String toAddress;
    private String toCity;
    private String toProvince;
    private String toCode;
    private String toCountry;
    private Integer courierServiceId;
    private Set<ShipmentModel> shipments;

    public OrderCreationRequestModel(Integer userId, DateTime orderDate, String fromAddress, String fromCity, String fromProvince, String fromCode, String fromCountry, String toAddress, String toCity, String toProvince, String toCode, String toCountry, Integer courierServiceId, Set<ShipmentModel> shipments) {
        this.userId = userId;
        this.orderDate = orderDate;
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
        this.shipments = shipments;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
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

    public Integer getCourierServiceId() {
        return courierServiceId;
    }

    public void setCourierServiceId(Integer courierServiceId) {
        this.courierServiceId = courierServiceId;
    }

    public Set<ShipmentModel> getShipments() {
        return shipments;
    }

    public void setShipments(Set<ShipmentModel> shipments) {
        this.shipments = shipments;
    }
}
