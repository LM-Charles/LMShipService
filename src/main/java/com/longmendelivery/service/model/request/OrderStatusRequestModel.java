package com.longmendelivery.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by desmond on 21/06/15.
 */
public class OrderStatusRequestModel implements Model {
    @JsonProperty

    String status;
    @JsonProperty
    String statusDescription;

    public OrderStatusRequestModel() {
    }

    public OrderStatusRequestModel(String status, String statusDescription) {

        this.status = status;
        this.statusDescription = statusDescription;
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


}
