package com.longmendelivery.service.model.shipment;

import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.lib.client.shipment.longmendelivery.LongmenShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.lib.client.shipment.rocketshipit.parser.FedexTrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.parser.TrackingResponseParser;
import com.longmendelivery.lib.client.shipment.rocketshipit.parser.UPSTrackingResponseParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum CourierType {
    UPS("UPS", RSIShipmentClient.class, "02", new UPSTrackingResponseParser()),
    FEDEX("fedex", RSIShipmentClient.class, "YOUR_PACKAGING", new FedexTrackingResponseParser()),
    CANADA_POST("canada", RSIShipmentClient.class, "", null),
    LONGMEN("longmen", LongmenShipmentClient.class, "", null);

    public static EnumSet<CourierType> ALL = EnumSet.allOf(CourierType.class);
    public static EnumSet<CourierType> ENABLED = EnumSet.of(FEDEX);

    private final String apiServiceId;
    private final Class<? extends ShipmentClient> shipmentClient;
    private final String selfPackagingCode;
    private final TrackingResponseParser trackingResponseParser;
}
