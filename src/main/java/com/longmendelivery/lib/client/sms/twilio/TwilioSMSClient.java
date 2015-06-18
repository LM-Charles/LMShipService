package com.longmendelivery.lib.client.sms.twilio;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.SMSClient;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 04/06/15.
 */
public class TwilioSMSClient implements SMSClient {
    public String sendSMS(String toNumber, String body) throws DependentServiceException {
        TwilioRestClient client = new TwilioRestClient(TwilioTestCredentials.ACCOUNT_SID, TwilioTestCredentials.AUTH_TOKEN);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", body));
        params.add(new BasicNameValuePair("To", toNumber));
        params.add(new BasicNameValuePair("From", TwilioTestCredentials.FROM));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        try {
            return messageFactory.create(params).getSid();
        } catch (TwilioRestException e) {
            throw new DependentServiceException(e);
        }
    }
}