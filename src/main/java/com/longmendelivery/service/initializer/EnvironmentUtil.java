package com.longmendelivery.service.initializer;

/**
 * Created by desmond on 09/08/15.
 */
public class EnvironmentUtil {
    public static EnvironmentStage getStage() {
        return EnvironmentStage.valueOf(System.getProperty("EBS_STAGE"));
    }
}
