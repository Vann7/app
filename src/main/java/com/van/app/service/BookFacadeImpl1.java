package com.van.app.service;

import org.springframework.stereotype.Service;

@Service
public class BookFacadeImpl1 {
    public String addBook(String name) {
        System.out.println("新增图书...");
        return name + "购买新书";
    }
}
