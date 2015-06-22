package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by desmond on 20/06/15.
 */
public class MessageResponseModel implements Model {
    @JsonProperty
    private String message;

    public MessageResponseModel() {
    }

    public MessageResponseModel(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
