package com.longmendelivery.lib.client.shipment.rocketshipit.script;

import com.longmendelivery.service.model.shipment.CourierType;

import java.util.EnumSet;

/**
 * Created by  rabiddesireon 05/07/15.
 */
public class TrackScriptGenerator {
    public static final EnumSet<CourierType> SUPPORTED = EnumSet.of(CourierType.FEDEX, CourierType.UPS, CourierType.CANADA_POST);

    private final CourierType courierType;
    private String trackingNumber;

    public TrackScriptGenerator(CourierType courierType) {
        validate(courierType);
        this.courierType = courierType;
    }

    public TrackScriptGenerator withTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }


    public String generate() {
        String script = "$t = new \\RocketShipIt\\Track('" + courierType.getApiServiceId() + "');\n" +
                "$response = $t->track('" + trackingNumber + "');\n" +
                "echo json_encode($response);";
        return script;
    }

    protected void validate(CourierType courierType) {
        if (!SUPPORTED.contains(courierType)) {
            throw new IllegalArgumentException("RocketShip tracking does not support courier: " + courierType);
        }
    }

}


