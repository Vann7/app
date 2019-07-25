package com.van.app.service;

import com.van.app.model.User;

import java.util.List;

public interface UserService {

    public static final int a = 2;

    /**
     * 静态方法
     */
    static void showStatic() {
        System.out.println("InterfaceA++showStatic");
    }



   default int defTest() {
       System.out.println("interface default");
       return 1;
   }

    List<User> getList();

    User getOne(int id);

    int update(User user);

    User getUserByName(String name, int age);

    int addOne(User user);
}
