package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import com.longmendelivery.service.model.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by desmond on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserDetailRequestModel implements Model {

    @JsonProperty
    @NonNull
    private String phone;
    @JsonProperty
    @NonNull
    private String email;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;

    @JsonProperty
    private AddressModel newAddress;

}
