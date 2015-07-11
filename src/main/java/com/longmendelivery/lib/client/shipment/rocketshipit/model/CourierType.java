package com.longmendelivery.lib.client.shipment.rocketshipit.model;

/**
 * Created by desmond on 11/07/15.
 */
public enum CourierType {
    UPS("UPS"), FEDEX("fedex"), CANADA_POST("canada");

    private final String apiServiceId;

    CourierType(String apiServiceId) {
        this.apiServiceId = apiServiceId;
    }

    public String getApiServiceId() {
        return apiServiceId;
    }
}
