package com.longmendelivery.service.model.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSIRateEntry {
    @JsonProperty(value = "desc")
    private String description;
    @JsonProperty(value = "rate")
    private String rate;
    @JsonProperty(value = "service_code")
    private String serviceCode;
}