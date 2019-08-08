package com.van.app.util;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/8/7
 * Time: 10:51 AM
 * 饿汉模式 单例模式
 */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton(){}

    public Singleton getInstance() {
        return singleton;
    }
}
