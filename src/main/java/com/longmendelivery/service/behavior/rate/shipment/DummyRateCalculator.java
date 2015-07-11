package com.longmendelivery.service.behavior.rate.shipment;

import com.longmendelivery.service.model.RateEntryModel;
import com.longmendelivery.service.model.request.RateRequestModel;
import com.longmendelivery.service.model.response.RateResponseModel;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 05/07/15.
 */
public class DummyRateCalculator implements RateCalculator {
    @Override
    public RateResponseModel calculate(RateRequestModel rateRequestModel) {
        List<RateEntryModel> rates = new ArrayList<>();
        RateEntryModel dummyModel = new RateEntryModel("", "LMSameCity", BigDecimal.ZERO, "LongMenDelivery", "LongMenSameCityCourier", DateTime.now());
        RateEntryModel serviceRate = new RateEntryModel("", "LMSameCity", new BigDecimal("5.00"), "LongMenDelivery", "LongMenSameCityHandling", DateTime.now());
        rates.add(dummyModel);
        return new RateResponseModel(DateTime.now(), rates, serviceRate);
    }
}
