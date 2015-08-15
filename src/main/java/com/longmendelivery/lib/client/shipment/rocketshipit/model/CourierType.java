package com.longmendelivery.lib.client.shipment.rocketshipit.model;

import com.longmendelivery.lib.client.shipment.rocketshipit.parser.FedexTrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.parser.TrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.parser.UPSTrackingResponseParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum CourierType {
    UPS("UPS", "02", new UPSTrackingResponseParser()), FEDEX("fedex", "YOUR_PACKAGING", new FedexTrackingResponseParser()), CANADA_POST("canada", "", null);

    public static EnumSet<CourierType> ALL = EnumSet.allOf(CourierType.class);
    public static EnumSet<CourierType> ENABLED = EnumSet.of(FEDEX);

    private final String apiServiceId;
    private final String selfPackagingCode;
    private final TrackingResponseParser trackingResponseParser;
}
