package com.longmendelivery.service.behavior;

import com.longmendelivery.service.model.OrderCreationRequestModel;

import java.math.BigDecimal;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public interface OrderCalculator {
    BigDecimal estimate(OrderCreationRequestModel orderCreationRequestModel);
}