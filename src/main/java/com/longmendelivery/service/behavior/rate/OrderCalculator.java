package com.longmendelivery.service.behavior.rate;

import com.longmendelivery.service.model.request.OrderCreationRequestModel;

import java.math.BigDecimal;

/**
 * Created by desmond on 20/06/15.
 */
public interface OrderCalculator {
    BigDecimal estimate(OrderCreationRequestModel orderCreationRequestModel);
}
