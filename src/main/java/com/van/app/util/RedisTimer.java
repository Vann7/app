package com.van.app.util;

import com.van.app.model.User;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
//@EnableAsync        // 2.开启多线程
public class RedisTimer {

    @Autowired
    private JmsMessagingTemplate template;


    //3.添加定时任务
//    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=1000)
    private void configureTasks() {

//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        Destination destination = new ActiveMQQueue("mytest.queue");

        template.convertAndSend(destination,"time is :" + LocalDateTime.now());
    }

  /*  @Bean
    public RedisTimer initMethod() {

        System.err.println("initMethod");
        return new RedisTimer();
    }*/

}
