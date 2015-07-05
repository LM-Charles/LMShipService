package com.longmendelivery.service.security;

/**
 * Created by desmond on 20/06/15.
 */
public class NotAuthorizedException extends Exception {
    private String responseMessage;

    public NotAuthorizedException(String responseMessage) {
        super(responseMessage);
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
