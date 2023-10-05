package com.example.service;


import com.example.thread.SendPhoneThread;
import com.example.util.RandomUtil;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {
    @Autowired
    private SmsHistoryService smsHistoryService;
    public void sendPhoneVerification(String phone) {

        Integer message = RandomUtil.get();  //  get random sms code
        smsHistoryService.save(phone, message.toString()); // save sms history
        SendPhoneThread sendPhoneThread = new SendPhoneThread(phone, message.toString());
        sendPhoneThread.start();
    }
}
