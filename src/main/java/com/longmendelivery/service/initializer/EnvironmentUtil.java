package com.longmendelivery.service.initializer;

/**
 * Created by desmond on 09/08/15.
 */
public class EnvironmentUtil {
    public static EnvironmentStage getStage() {
        String ebsStage = System.getProperty("EBS_STAGE");
        System.out.println(ebsStage);
        if (ebsStage == null || ebsStage.isEmpty()) {
            return EnvironmentStage.DESKTOP;
        }
        return EnvironmentStage.valueOf(ebsStage);
    }

}
