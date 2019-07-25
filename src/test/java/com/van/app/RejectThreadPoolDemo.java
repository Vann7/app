package com.van.app;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/24
 * Time: 9:59 AM
 */
public class RejectThreadPoolDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " : Thread:ID: " +
                    Thread.currentThread().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    System.out.println("create " + t);
                    return t;
                },
                (r, executor) -> {
                    System.out.println(r.toString() + " is discard");
                });

        for (int i=0; i< Integer.MAX_VALUE; i++) {
            es.execute(myTask);
            Thread.sleep(2000);
        }
    }

}
