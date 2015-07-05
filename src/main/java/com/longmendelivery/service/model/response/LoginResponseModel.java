package com.longmendelivery.service.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by desmond on 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseModel implements Model {
    @JsonProperty
    @NonNull
    private Integer id;

    @JsonProperty
    @NonNull
    private String apiToken;

}
