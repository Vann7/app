package com.van.app.controller;

import com.google.gson.Gson;
import com.van.app.model.User;
import com.van.app.proxy.BookFacadeCglibProxy;
import com.van.app.proxy.DynamicProxy;
import com.van.app.proxy.UserServiceProxy;
import com.van.app.service.BookFacadeImpl1;
import com.van.app.service.UserService;
import com.van.app.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    @Qualifier(value = "userServiceImpl")
    private UserService userServiceImpl;
    @Autowired
    @Qualifier("userServiceProxy")
    private UserServiceProxy userServiceProxy;
    @Autowired
    RedisTemplate<String, String> template;
    @Autowired
    DynamicProxy proxy;
    @Autowired
    BookFacadeCglibProxy cgLibProxy;
    @Autowired
    BookFacadeImpl1 bookFacadeImpl1;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/login")
    public String login(String name, int age, HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        map.put("name", name);
        map.put("age", String.valueOf(age));
        User user = userServiceImpl.getUserByName(name, age);
        if (user != null) {
            String key = session.getId();
//            template.opsForValue().set(key, UUID.randomUUID().toString(), 60L);
            boolean flag = template.opsForValue().setIfAbsent(key, UUID.randomUUID().toString(), 300L, TimeUnit.SECONDS);
            logger.info(name + " 登录");
            return flag == true ? "登陆成功" : "登录失败";
        }
        logger.info(name + " 登录");
        return "登录失败";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String key = session.getId();
        boolean flag = template.delete(key);
        logger.info("退出");
        return flag == true ? "退出成功" : "退出失败";
    }


    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        int flag = userServiceImpl.addOne(user);
        if (flag == 0) {
            logger.info("新增用户: " + user.getName() +" 失败");
            return "failed!";
        } else {
            logger.info("新增用户: " + user.getName() +" 成功");
            return "success!";
        }
    }


    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public List<User> getList() {
        List<User> list = userServiceProxy.getList();
        return list;
    }

    @RequestMapping(value = "getOne/{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable("id") int id , HttpServletRequest request) {
        HttpSession session = request.getSession();
        String key = session.getId();
        String result = template.opsForValue().get(key);
        if (result == null) {
            return "请重新登录";
        } else { //重置session过期时间
            template.expire(key, 60L, TimeUnit.SECONDS);
        }
        User user = userServiceImpl.getOne(id);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateUser(@RequestBody User user) {
        int flag = userServiceImpl.update(user);
        return flag == 1 ? "success" : "failed";
    }


    @RequestMapping(value = "getList2", method = RequestMethod.GET)
    public List<User> getList2() {
        UserService service = (UserService) proxy.bind(userServiceProxy);
        List<User> list =  service.getList();
//        List<User> list = userServiceProxy.getList();
        return list;
    }

    @GetMapping("getBook/{name}")
    public String getOneByName(@PathVariable("name") String name) {
        BookFacadeImpl1 bookCglib = (BookFacadeImpl1) cgLibProxy.getInstance(bookFacadeImpl1);
        String result = bookCglib.addBook(name);
        return result;
    }

}
