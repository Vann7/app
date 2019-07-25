package com.van.app.controller;

import com.google.gson.Gson;
import com.van.app.service.JmsService;
import com.van.app.service.UserService;
import com.van.app.model.User;
import com.van.app.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {

    @Autowired
    @Qualifier(value = "userServiceImpl")
    UserService userService;
    @Autowired
    JmsService jmsService;

    @Autowired
    RedisLock redisLock;

    @Autowired
    RedisTemplate<String, String> template;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        System.out.println("test");
        redisLock.setKey("hello2");
        redisLock.lock();
        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            redisLock.unlock();
        }


        return "登录页, 请登录系统!";
    }




    @GetMapping(value = "jms")
    public String getMessage() {
        String result = jmsService.getMessage();
        return result;
    }

    /*
     * 监听和读取消息
     */
//    @JmsListener(destination="mytest.queue")
    public void readActiveQueue(String message) {
        System.out.println("queue1, 接受到：" + message);

        //TODO something
    }

    /*
     * 监听和读取消息
     */
//    @JmsListener(destination="mytest.queue")
    public void readActiveQueue2(String message) {
        template.opsForSet().add("mq", message);
        System.out.println("queue2, 接受到：" + message);
    }





}
