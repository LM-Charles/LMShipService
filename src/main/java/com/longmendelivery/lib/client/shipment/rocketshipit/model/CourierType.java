package com.longmendelivery.lib.client.shipment.rocketshipit.model;

import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.FedexTrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.TrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.scripts.UPSTrackingResponseParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum CourierType {
    UPS("UPS", "02", new UPSTrackingResponseParser()), FEDEX("fedex", "01", new FedexTrackingResponseParser()), CANADA_POST("canada", "", null);

    public static EnumSet<CourierType> ALL = EnumSet.allOf(CourierType.class);
    public static EnumSet<CourierType> ENABLED = EnumSet.of(UPS);

    private final String apiServiceId;
    private final String selfPackagingCode;
    private final TrackingResponseParser trackingResponseParser;
}
