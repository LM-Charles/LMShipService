package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import org.apache.commons.lang.NotImplementedException;

import java.util.EnumSet;

/**
 * Created by  rabiddesireon 05/07/15.
 */
public class TrackScript {
    public static final EnumSet<CourierType> SUPPORTED = EnumSet.of(CourierType.FEDEX, CourierType.UPS, CourierType.CANADA_POST);

    String generate(final CourierType courierType, final String track) {
        validate(courierType);
        String script = "$t = new \\RocketShipIt\\Track('" + courierType + "');\n" +
                "$response = $t->track('" + track + "');\n" +
                "echo json_encode($response);";
        return script;
    }

    protected void validate(CourierType courierType) {
        if (!SUPPORTED.contains(courierType)) {
            throw new NotImplementedException("RocketShip tracking does not support courier: " + courierType);
        }
    }

}


