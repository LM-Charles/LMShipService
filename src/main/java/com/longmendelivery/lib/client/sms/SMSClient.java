package com.longmendelivery.lib.client.sms;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;

/**
 * Created by desmond on 04/06/15.
 */
public interface SMSClient {
    String sendSMS(String phoneNumber, String content) throws DependentServiceException;
}
