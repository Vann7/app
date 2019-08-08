package com.van.app.util;


/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/8/7
 * Time: 10:52 AM
 * 懒汉模式 单例模式
 */
public class SingletonLazy {

    private static volatile SingletonLazy instance = null;

    private SingletonLazy(){}


    public SingletonLazy getInstance() {
        if (instance == null) {
            synchronized (SingletonLazy.class) {
                if (instance == null) {
                    instance = new SingletonLazy();
                }
            }
        }

        return instance;
    }

}
