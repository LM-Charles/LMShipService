package com.longmendelivery.service.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.RateEntryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponseModel {
    @JsonProperty
    @NonNull
    DateTime rateDate;

    @NonNull
    List<RateEntryModel> rates;
}