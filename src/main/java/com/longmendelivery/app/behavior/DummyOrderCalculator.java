package com.longmendelivery.app.behavior;

import com.longmendelivery.app.model.OrderCreationRequestModel;

import java.math.BigDecimal;

/**
 * Created by desmond on 20/06/15.
 */
public class DummyOrderCalculator implements OrderCalculator {
    @Override
    public BigDecimal estimate(OrderCreationRequestModel orderCreationRequestModel) {
        return BigDecimal.ONE;
    }
}
