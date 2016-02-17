package com.longmendelivery.lib.client.exceptions;

/**
 * Created by rabiddesire on 04/06/15.
 */
public class DependentServiceException extends Exception {
    public DependentServiceException(Throwable e) {
        super(e);
    }


    public DependentServiceException() {

    }


    public DependentServiceException(String message) {
        super(message);
    }
}
