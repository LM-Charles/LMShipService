package com.longmendelivery.lib.client.shipment.rocketshipit;

import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by  rabiddesireon 21/06/15.
 */
public class RocketShipItJavaRunner {
    public String runLibrary(String script) throws ScriptException {
        String code =
                "require 'autoload.php'; // This autoloads RocketShipIt classes\n" +
                        "\n" +
                        "$rate = new \\RocketShipIt\\Rate('UPS');\n" +
                        "\n" +
                        "$rate->setParameter('shipCode','V4C0A9');\n" +
                        "$rate->setParameter('shipCountry','CA');\n" +
                        "$rate->setParameter('toCode','V7C2X4');\n" +
                        "$rate->setParameter('toCountry','CA');\n" +
                        "$rate->setParameter('weight','5');\n" +
                        "$rate->setParameter('residentialAddressIndicator','0');\n" +
                        "$rate->setParameter('packagingType','02');\n" +
                        "\n" +
                        "$response = $rate->getsimplerates();\n" +
                        "print_r($response);\n";

        ScriptEngine engine = new InteractivePhpScriptEngineFactory().getScriptEngine();
        try {
            String value = (String) engine.eval(script);
            return value;
        } catch (ScriptException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
