package com.longmendelivery.lib.client.shipment.rocketshipit.model;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class ShippingDimension {
    private final Integer length;
    private final Integer width;
    private final Integer height;

    private final Integer weight;


    public ShippingDimension(Integer length, Integer width, Integer height, Integer weight) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public ShippingDimension normalized() {
        return this;
    }
}
