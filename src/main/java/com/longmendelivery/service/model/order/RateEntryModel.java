package com.longmendelivery.service.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateEntryModel {
    @JsonProperty
    @NonNull
    private String service_icon_url;
    @JsonProperty
    @NonNull
    private String category;
    @JsonProperty
    @NonNull
    private BigDecimal estimate;
    @JsonProperty
    @NonNull
    private String courierName;
    @JsonProperty
    @NonNull
    private String serviceName;

    @JsonProperty
    private DateTime estimatedDelivery;
}
