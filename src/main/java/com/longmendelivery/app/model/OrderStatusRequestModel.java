package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by desmond on 21/06/15.
 */
public class OrderStatusRequestModel implements Model {
    public OrderStatusRequestModel() {
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public OrderStatusRequestModel(String status, String statusDescription) {

        this.status = status;
        this.statusDescription = statusDescription;
    }

    @JsonProperty

    String status;
    @JsonProperty
    String statusDescription;


}
