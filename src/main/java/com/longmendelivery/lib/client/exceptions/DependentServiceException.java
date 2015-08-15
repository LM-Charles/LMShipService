package com.longmendelivery.lib.client.exceptions;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class DependentServiceException extends Exception {
    public DependentServiceException(Throwable e) {
        super(e);
    }


    public DependentServiceException() {

    }
}
