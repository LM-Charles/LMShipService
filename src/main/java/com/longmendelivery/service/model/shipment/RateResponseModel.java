package com.longmendelivery.service.model.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.order.RateEntryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by  rabiddesireon 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponseModel {
    @JsonProperty
    @NonNull
    DateTime rateDate;

    @NonNull
    List<RateEntryModel> courierRates;

    @NonNull
    RateEntryModel handlingRate;

    @NonNull
    RateEntryModel insuranceRate;
}
