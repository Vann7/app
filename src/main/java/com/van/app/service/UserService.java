package com.van.app.service;

import com.van.app.model.User;

import java.util.List;

public interface UserService {

    List<User> getList();

    User getOne(int id);

    int update(User user);
}
