package com.longmendelivery.service.model;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class PackageDimensionModel {
    private final Integer length;
    private final Integer width;
    private final Integer height;

    private final Integer weight;


    public PackageDimensionModel(Integer length, Integer width, Integer height, Integer weight) {
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

    public PackageDimensionModel normalized() {
        return this;
    }
}
