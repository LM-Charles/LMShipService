package com.longmendelivery.service.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by desmond on 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentTrackingResponseModel {
    @JsonProperty
    @NonNull
    DateTime pickUpDate;

    @JsonProperty
    @NonNull
    DateTime trackingDate;

    @JsonProperty
    @NotNull
    String trackingLocation;

    @JsonProperty
    @NotNull
    String trackingStatus;
}
