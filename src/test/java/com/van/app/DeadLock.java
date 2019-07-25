package com.van.app;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/24
 * Time: 2:35 PM
 */
public class DeadLock implements Runnable {

    protected Object tool;
    static Object fork1 = new Object();
    static Object fork2 = new Object();

    public DeadLock(Object tool) {
        this.tool = tool;
        if (tool == fork1) {

        }
    }

    @Override
    public void run() {
        if (tool == fork1) {
            synchronized (fork1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2) {
                    System.out.println("A 开始吃饭");
                }
            }
        }

        if (tool == fork2) {
            synchronized (fork2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork1) {
                    System.out.println("B 开始吃饭");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock dl_a = new DeadLock(fork1);
        DeadLock dl_b = new DeadLock(fork2);
        new Thread(dl_a).start();
        new Thread(dl_b).start();
        Thread.sleep(2000);
    }


}
