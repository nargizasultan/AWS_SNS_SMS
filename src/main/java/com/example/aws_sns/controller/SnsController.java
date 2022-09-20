package com.example.aws_sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SnsController {

    @Autowired
    private AmazonSNSClient amazonSNSClient;
    private final String ARN_SNS_TOPIC = "arn";



    @GetMapping("/subscribe/{phoneNumber}")
    public String subscribe(@PathVariable String phoneNumber) {

        SubscribeRequest subscribeRequest =
                new SubscribeRequest(ARN_SNS_TOPIC, "sms",phoneNumber);
        amazonSNSClient.subscribe(subscribeRequest);
        return "PhoneNumber subscription successful";

    }

    @GetMapping("/publish/{message}")
    public String sendMessage(@PathVariable String message){

        Map<String, MessageAttributeValue> smsAttributes=new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID")
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice",new MessageAttributeValue()
                .withStringValue("0.50")
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType",new MessageAttributeValue()
                        .withStringValue("Transactional")
                        .withDataType("String"));


        PublishRequest publishRequest=new PublishRequest();
        publishRequest.setMessage(message);
        publishRequest.setTopicArn(ARN_SNS_TOPIC);
        publishRequest.setMessageAttributes(smsAttributes);
        amazonSNSClient.publish(publishRequest);
        return "Message published successfully";
    }

}
