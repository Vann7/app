package com.van.app.proxy;

import com.van.app.model.User;
import com.van.app.service.UserService;
import com.van.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 静态代理类
 */
@Service("userServiceProxy")
public class UserServiceProxy implements UserService {

    @Autowired
    private UserServiceImpl userService;


    @Override
    public List<User> getList() {
        System.out.println("代理开始");
        List<User> users =  userService.getList();

        users.add(0,new User(1,"haha", 123));
        System.out.println("代理结束，共计: " + users.size() + " 个人员");
        return users;
    }

    @Override
    public User getOne(int id) {
        return null;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public User getUserByName(String name, int age) {
        return null;
    }

    @Override
    public int addOne(User user) {
        return 0;
    }
}
