package com.longmendelivery.app.behavior;

/**
 * Created by desmond on 20/06/15.
 */
public class OrderCalculatorProvider {
    public static OrderCalculator provide(Integer courierServiceId) {
        return new DummyOrderCalculator();
    }
}
