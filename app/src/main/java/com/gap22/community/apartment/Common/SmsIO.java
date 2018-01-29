package com.gap22.community.apartment.Common;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SmsIO {

    public static void SendSMS(String phoneNumber, String message) {
        Twilio.init(GlobalValues.ACCOUNT_SID, GlobalValues.AUTH_TOKEN);
        MessageCreator messageCreator = Message.creator("AC7700be57cfe831a28fb5a9d96126f10b", new PhoneNumber("+918667296321"), new PhoneNumber("+919840399445"), "Message From Communify");
        //MessageCreator messageCreator = Message.creator("AC7700be57cfe831a28fb5a9d96126f10b", new PhoneNumber(phoneNumber), message);
        Message messageObject = messageCreator.create();
        String errormessage = messageObject.getErrorMessage();
    }
}
