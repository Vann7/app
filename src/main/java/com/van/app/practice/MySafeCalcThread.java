package com.van.app.practice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java线程安全的计数器
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/15
 * Time: 3:25 PM
 */
public class MySafeCalcThread implements Runnable {

    private static volatile AtomicInteger count = new AtomicInteger(0);
    private static ReentrantLock lock = new ReentrantLock();

    private static void calc() {
        int c = 0;
        synchronized (count) {

//        lock.lock();
            if (count.get() <= 1000) {
                c = count.getAndIncrement();
                System.out.println("当前线程: " + Thread.currentThread().getName() + " - " + c);
            }

        }
//        lock.unlock();
    }

    @Override
    public void run() {
        while (true) {
            int temp = count.get();
            if (temp >= 1000) break;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MySafeCalcThread.calc();
        }
        System.out.println("-----------"+ Thread.currentThread().getName() + "---------------" + "OVER!!!!!");
    }


    public static void main(String[] args) {
        for (int i = 0; i< 6; i++) {
            Thread thread = new Thread(new MySafeCalcThread());
            thread.start();
        }
    }

}
