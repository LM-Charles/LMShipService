package com.longmendelivery.service.behavior;

import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;

/**
 * Created by desmond on 05/07/15.
 */
public interface RateCalculator {
    RateResponseModel calculate(RateRequestModel rateRequestModel);
}
