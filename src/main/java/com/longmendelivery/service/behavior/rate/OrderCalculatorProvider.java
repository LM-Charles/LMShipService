package com.longmendelivery.service.behavior.rate;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class OrderCalculatorProvider {
    public static OrderCalculator provide(Integer courierServiceId) {
        return new DummyOrderCalculator();
    }
}
