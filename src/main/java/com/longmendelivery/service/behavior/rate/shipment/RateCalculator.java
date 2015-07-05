package com.longmendelivery.service.behavior.rate.shipment;

import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;

/**
 * Created by  rabiddesireon 05/07/15.
 */
public interface RateCalculator {
    RateResponseModel calculate(RateRequestModel rateRequestModel);
}
