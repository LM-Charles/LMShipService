package com.longmendelivery.service.security;

/**
 * Created by rabiddesire on 04/06/15.
 */
public class ThrottleSecurity {
    private static ThrottleSecurity instance;

    private ThrottleSecurity() {

    }

    public static ThrottleSecurity getInstance() {
        if (instance == null) {
            instance = new ThrottleSecurity();
        }
        return instance;
    }

    public void throttle(Object key) {
    }

    public void throttle() {
    }
}
