package com.van.app.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class JmsServiceImpl implements JmsService {




    @Override
    public String getMessage() {
        return jmsMessage("123");
    }

    @JmsListener(destination = "time")
    private String jmsMessage(String message){
        return message;
    }

}
