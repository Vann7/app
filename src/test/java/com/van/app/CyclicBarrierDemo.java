package com.van.app;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/23
 * Time: 4:32 PM
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable {

        private String soldier;
        private final CyclicBarrier barrier;

        public Soldier(String soldier, CyclicBarrier barrier) {
            this.soldier = soldier;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                beforeWork();
                barrier.await();
                doWork();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void beforeWork() {
            try {
                Thread.sleep(new Random().nextInt(10) * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("士兵" + soldier + " 报道！");
        }


        private void doWork() {
            try {
                Thread.sleep(new Random().nextInt(10) * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + " 完成任务！");
        }
    }


    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令:[士兵 " + N + " 个，任务完成!]");
            } else {
                System.out.println("司令:[士兵 " + N + " 个，集合完成!]");
                flag = true;
            }
        }
    }


    public static void main(String[] args) {
        final int N = 3;
        boolean flag = false;
        CyclicBarrier barrier = new CyclicBarrier(N, new BarrierRun(flag, N));
        ExecutorService es = Executors.newFixedThreadPool(N);
        ScheduledExecutorService es2 = Executors.newScheduledThreadPool(10);
        es2.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

        System.out.println("集合队伍");
        for (int i=0; i< N; ++i) {
            es2.execute(new Soldier("士兵" + i,  barrier));
        }
//        System.out.println("任务结束!");
        es2.shutdown();
    }
}
