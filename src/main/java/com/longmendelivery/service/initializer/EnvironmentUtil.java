package com.longmendelivery.service.initializer;

import com.longmendelivery.lib.client.sms.twilio.TwilioStage;

/**
 * Created by  rabiddesireon 09/08/15.
 */
public class EnvironmentUtil {
    public static EnvironmentStage getStage() {
        String ebsStage = System.getProperty("EBS_STAGE");
        if (ebsStage == null || ebsStage.isEmpty()) {
            return EnvironmentStage.DESKTOP;
        }
        return EnvironmentStage.valueOf(ebsStage.toUpperCase());
    }

    public static String getApplicationPHPRoot() {
        String applicationPHPRoot = System.getProperty("APP_PHP_ROOT");
        return applicationPHPRoot;
    }

    public static TwilioStage getTwilioStage() {
        String twilioStage = System.getProperty("TWILIO_STAGE");
        if (twilioStage != null && !twilioStage.isEmpty() && twilioStage.equals(TwilioStage.PROD.name())) {
            return TwilioStage.PROD;
        }
        return TwilioStage.TEST;
    }

    public static boolean getAPISecurity() {
        String apiSecurity = System.getProperty("API_SECURITY");
        if (apiSecurity != null && !apiSecurity.isEmpty() && apiSecurity.equals("enabled")) {
            return true;
        } else {
            return false;
        }
    }
}
