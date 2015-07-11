package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import org.apache.commons.lang.NotImplementedException;

import java.util.EnumSet;

/**
 * Created by  rabiddesireon 05/07/15.
 */
public class RateScript {
    public static final EnumSet<CourierType> SUPPORTED = EnumSet.of(CourierType.FEDEX, CourierType.UPS, CourierType.CANADA_POST);

    private static final String SELF_PACKAGING = "2";
    private static final String INT_FALSE = "0";

    String generate(final CourierType courierType, final String toPostal, final String toCountry, String fromPostal, String fromCountry, final int weight) {
        validate(courierType);
        String script = "\n" +
                "    $rate = new \\RocketShipIt\\Rate('" + courierType + "');\n" +
                "    $rate->setParameter('shipCode','" + fromPostal + "');\n" +
                "    $rate->setParameter('shipCountry','" + fromCountry + "');\n" +
                "    $rate->setParameter('toCode','" + toPostal + "');\n" +
                "    $rate->setParameter('toCountry','" + toCountry + "');\n" +
                "    $rate->setParameter('weight','" + weight + "');\n" +
                "    $rate->setParameter('residentialAddressIndicator','" + INT_FALSE + "');\n" +
                "    $rate->setParameter('packagingType','" + SELF_PACKAGING + "');\n" +
                "    $response = $rate->getSimpleRates();\n" +
                "    echo json_encode($response);";
        return script;
    }

    protected void validate(CourierType courierType) {
        if (!SUPPORTED.contains(courierType)) {
            throw new NotImplementedException("RocketShip tracking does not support courier: " + courierType);
        }
    }
}
