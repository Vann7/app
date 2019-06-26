package com.van.app.controller;

import com.van.app.service.UserService;
import com.van.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        System.out.println("test");
        return "success";
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public List<User> getList() {
        List<User> list = userService.getList();
        return list;
    }

    @RequestMapping(value = "getOne/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable("id") int id) {
        User user = userService.getOne(id);
        return user;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateUser(@RequestBody User user) {
        int flag = userService.update(user);
        return flag == 1 ? "success" : "failed";
    }

}
