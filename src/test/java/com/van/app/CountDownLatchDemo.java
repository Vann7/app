package com.van.app;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/23
 * Time: 4:16 PM
 */
public class CountDownLatchDemo implements Runnable {

    static final CountDownLatchDemo demo = new CountDownLatchDemo();
    static final CountDownLatch cdl = new CountDownLatch(10);

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(Thread.currentThread().getName() + ": check complete!");
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i=0; i< 10; i++) {
            es.submit(demo);
        }
        cdl.await();
        System.out.println("Fire!!!");
        es.shutdown();
    }

}
