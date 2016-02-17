package com.longmendelivery.service.model.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.order.RateEntryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by rabiddesire on 05/07/15.
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

    public BigDecimal calculateTotalWithService(CourierServiceType courierServiceType) {
        boolean isFound = false;
        BigDecimal total = BigDecimal.ZERO;
        for (RateEntryModel rate : courierRates) {
            if (rate.getServiceName().equals(courierServiceType.name())) {
                total = total.add(rate.getEstimate()).add(rate.getTaxEstimate());
                isFound = true;
            }
        }

        if (!isFound) throw new NoSuchElementException("The specified service is not available from this quote");

        total = total.add(handlingRate.getEstimate()).add(handlingRate.getTaxEstimate());
        total = total.add(insuranceRate.getEstimate()).add(insuranceRate.getTaxEstimate());

        return total;
    }
}
