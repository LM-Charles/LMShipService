package com.longmendelivery.lib.client.sms;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.exceptions.DependentServiceRequestException;

/**
 * Created by rabiddesire on 04/06/15.
 */
public interface SMSClient {
    String sendSMS(String phoneNumber, String content) throws DependentServiceException, DependentServiceRequestException;
}
