package com.longmendelivery.lib.client.exceptions;

import com.twilio.sdk.TwilioRestException;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class DependentServiceException extends Throwable {
    public DependentServiceException(TwilioRestException e) {
    }
}
