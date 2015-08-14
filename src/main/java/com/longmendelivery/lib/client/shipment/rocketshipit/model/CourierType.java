package com.longmendelivery.lib.client.shipment.rocketshipit.model;

import java.util.EnumSet;

public enum CourierType {
    UPS("UPS", "02"), FEDEX("fedex", "01"), CANADA_POST("canada", "");

    public static EnumSet<CourierType> ALL = EnumSet.allOf(CourierType.class);
    public static EnumSet<CourierType> ENABLED = EnumSet.of(UPS);

    private final String apiServiceId;
    private final String selfPackagingCode;

    CourierType(String apiServiceId, String selfPackagingCode) {
        this.apiServiceId = apiServiceId;
        this.selfPackagingCode = selfPackagingCode;
    }

    public String getApiServiceId() {
        return apiServiceId;
    }

    public String getSelfPackagingCode() {
        return selfPackagingCode;
    }
}
