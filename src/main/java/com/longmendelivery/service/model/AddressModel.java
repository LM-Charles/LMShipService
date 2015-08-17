package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
    @JsonProperty
    @NonNull
    private String address = "";
    @JsonProperty
    @NonNull
    private String city = "";
    @JsonProperty
    @NonNull
    private String province = "";
    @JsonProperty
    @NonNull
    private String country = "";
    @JsonProperty
    @NonNull
    private String postal = "";

    @JsonProperty
    private Boolean residential = false;
}
