package com.longmendelivery.service.initializer;

/**
 * Created by  rabiddesireon 09/08/15.
 */
public class EnvironmentUtil {
    public static EnvironmentStage getStage() {
        String ebsStage = System.getProperty("EBS_STAGE");
        if (ebsStage == null || ebsStage.isEmpty()) {
            return EnvironmentStage.DESKTOP;
        }
        return EnvironmentStage.valueOf(ebsStage.toUpperCase());
    }

    public static String getApplicationPHPRoot() {
        String applicationPHPRoot = System.getProperty("APP_PHP_ROOT");
        return applicationPHPRoot;
    }
}
