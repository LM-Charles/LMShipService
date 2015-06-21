package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class OrderModel {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private Integer client;
    @JsonProperty
    private DateTime orderDate;
    @JsonProperty
    private Set<Integer> shipments;
    @JsonProperty
    private BigDecimal estimateCost;
    @JsonProperty
    private BigDecimal finalCost;
    @JsonProperty
    private String fromAddress;
    @JsonProperty
    private String fromCity;
    @JsonProperty
    private String fromProvince;
    @JsonProperty
    private String fromCode;
    @JsonProperty
    private String fromCountry;
    @JsonProperty
    private String toAddress;
    @JsonProperty
    private String toCity;
    @JsonProperty
    private String toProvince;
    @JsonProperty
    private String toCode;
    @JsonProperty
    private String toCountry;
    @JsonProperty
    private Integer courierServiceId;
    @JsonProperty
    private String handler;

    public OrderModel(Integer id, Integer client, DateTime orderDate, Set<Integer> shipments, BigDecimal estimateCost, BigDecimal finalCost, String fromAddress, String fromCity, String fromProvince, String fromCode, String fromCountry, String toAddress, String toCity, String toProvince, String toCode, String toCountry, Integer courierServiceId, String handler) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Set<Integer> getShipments() {
        return shipments;
    }

    public void setShipments(Set<Integer> shipments) {
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

    public Integer getCourierServiceId() {
        return courierServiceId;
    }

    public void setCourierServiceId(Integer courierServiceId) {
        this.courierServiceId = courierServiceId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public OrderModel() {

    }
}
