package com.longmendelivery.lib.client.shipment.rocketshipit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  rabiddesireon 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UPSRateResponseEntry {
    static List<UPSRateResponseEntry> parseEntries(String jsonString) {
        //xxx
        return new ArrayList<>();
    }

    @JsonProperty(value = "desc")
    @NonNull
    private String description;

    @JsonProperty(value = "rate")
    @NonNull
    private String rate;

    @JsonProperty(value = "service_code")
    @NonNull
    private String serviceCode;
}