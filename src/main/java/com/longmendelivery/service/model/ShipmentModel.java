package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class ShipmentModel implements Model {
    @JsonProperty
    private Integer height;
    @JsonProperty
    private Integer width;
    @JsonProperty
    private Integer length;
    @JsonProperty
    private Integer weight;

    @JsonProperty

    private String trackingNumber;
    @JsonProperty
    private String trackingDocumentType;

    public ShipmentModel() {
    }

    public ShipmentModel(Integer height, Integer width, Integer length, Integer weight) {
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }

    public ShipmentModel(Integer height, Integer width, Integer length, Integer weight, String trackingNumber, String trackingDocumentType) {
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.trackingNumber = trackingNumber;
        this.trackingDocumentType = trackingDocumentType;
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
