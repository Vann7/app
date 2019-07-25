package com.van.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/23
 * Time: 3:38 PM
 */
public class HashMapMultiThread {

    static Map<String, String> map = new HashMap();


    public static class AddThread implements Runnable {

        int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            synchronized (HashMapMultiThread.class) {
                for (int i = start; i< 100000; i += 2) {
                    map.put(Integer.toString(i), Integer.toBinaryString(i));
                }
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(100000);
        Thread thread1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread thread2 = new Thread(new HashMapMultiThread.AddThread(1));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(map.size());
    }

}
