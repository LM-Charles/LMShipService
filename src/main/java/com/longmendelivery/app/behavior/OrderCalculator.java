package com.longmendelivery.app.behavior;

import com.longmendelivery.app.model.OrderCreationRequestModel;

import java.math.BigDecimal;

/**
 * Created by desmond on 20/06/15.
 */
public interface OrderCalculator {
    BigDecimal estimate(OrderCreationRequestModel orderCreationRequestModel);
}
