package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.longmendelivery.service.initializer.EnvironmentStage;
import com.longmendelivery.service.initializer.EnvironmentUtil;

/**
 * Created by  rabiddesireon 09/08/15.
 */
public class RocketShipItUtil {
    public static String generateLineToRequireRocketShipItLibrary() {
        if (EnvironmentUtil.getStage().equals(EnvironmentStage.DESKTOP)) {
            return "require './src/main/php/php-rocket-shipit/autoload.php';";
        } else {
            return "require '/var/lib/tomcat7/webapps/ROOT/WEB-INF/classes/php-rocket-shipit/autoload.php';";
        }
    }
}
