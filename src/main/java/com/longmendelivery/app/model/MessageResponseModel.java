package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class MessageResponseModel {
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
