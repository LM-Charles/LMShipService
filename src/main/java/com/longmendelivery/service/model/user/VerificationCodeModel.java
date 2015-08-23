package com.longmendelivery.service.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeModel {
    @JsonProperty
    @NonNull
    private String code;
    @JsonProperty
    @NonNull
    private DateTime expiry;

}
