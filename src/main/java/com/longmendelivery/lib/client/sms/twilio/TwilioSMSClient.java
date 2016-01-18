package com.longmendelivery.lib.client.sms.twilio;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.exceptions.DependentServiceRequestException;
import com.longmendelivery.lib.client.sms.SMSClient;
import com.longmendelivery.service.initializer.EnvironmentUtil;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class TwilioSMSClient implements SMSClient {

    public static final int TWILIO_ERROR_INVALID_PHONE = 21211;

    public String sendSMS(String toNumber, String body) throws DependentServiceException, DependentServiceRequestException {
        TwilioRestClient client;
        String from;
        if (EnvironmentUtil.getTwilioStage().equals(TwilioStage.PROD)) {
            client = new TwilioRestClient(TwilioProdCredentials.ACCOUNT_SID, TwilioProdCredentials.AUTH_TOKEN);
            from = TwilioProdCredentials.FROM;
        } else {
            client = new TwilioRestClient(TwilioTestCredentials.ACCOUNT_SID, TwilioTestCredentials.AUTH_TOKEN);
            from = TwilioTestCredentials.FROM;

        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", body));
        params.add(new BasicNameValuePair("To", toNumber));
        params.add(new BasicNameValuePair("From", from));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        try {
            return messageFactory.create(params).getSid();
        } catch (TwilioRestException e) {
            if (e.getErrorCode() == TWILIO_ERROR_INVALID_PHONE) {
                throw new DependentServiceRequestException(e);
            } else {
                throw new DependentServiceException(e);
            }
        }
    }
}