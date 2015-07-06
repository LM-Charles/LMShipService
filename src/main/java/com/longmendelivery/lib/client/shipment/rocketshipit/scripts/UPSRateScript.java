package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

/**
 * Created by desmond on 05/07/15.
 */
public class UPSRateScript {
    public static final String UPS = "UPS";
    public static final int FALSE = 0;
    public static final int SELF_PACKAGING = 02;
    String load = "require './src/main/php/php-rocket-shipit/autoload.php';";


    String generate(final String toPostal, final String toCountry, String fromPostal, String fromCountry, final int weight) {
        String script = "\n" +
                "    $rate = new \\RocketShipIt\\Rate('" + UPS + "');\n" +
                "    $rate->setParameter('shipCode','" + fromPostal + "');\n" +
                "    $rate->setParameter('shipCountry','" + fromCountry + "');\n" +
                "    $rate->setParameter('toCode','" + toPostal + "');\n" +
                "    $rate->setParameter('toCountry','" + toCountry + "');\n" +
                "    $rate->setParameter('weight','" + weight + "');\n" +
                "    $rate->setParameter('residentialAddressIndicator','" + FALSE + "');\n" +
                "    $rate->setParameter('packagingType','" + SELF_PACKAGING + "');\n" +
                "    $response = $rate->getSimpleRates();\n" +
                "    echo json_encode($response);";
        return script;
    }

}
