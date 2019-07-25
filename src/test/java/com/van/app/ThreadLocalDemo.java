package com.van.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/7/24
 * Time: 2:05 PM
 */
public class ThreadLocalDemo {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static ThreadLocal<SimpleDateFormat> tl = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    public static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
//                if (tl.get() == null) {
//                    tl.set(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
//                }
                Date t = tl.get().parse("2019-07-24 14:07:" + i%60);
                System.out.println(i + ": " + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i=0; i< 100; i++) {
            es.execute(new ParseDate(i));
        }
        es.shutdown();
    }

}
