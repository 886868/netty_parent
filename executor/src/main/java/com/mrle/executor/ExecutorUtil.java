package com.mrle.executor;

import java.util.concurrent.*;

public class ExecutorUtil {

    public static void main(String[] args) {
        /*ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(5);

        Runnable task = () ->System.out.println("HeartBeat...............");

        executor.scheduleAtFixedRate(task, 5, 3, TimeUnit.SECONDS);*/
        m1();
    }

    public static void m1() {
        ExecutorService pool = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Runnable task = () ->{
            System.out.println("i am running...." + Thread.currentThread().getId());
        };

        for (int i = 0; i < 10; i++) {
            pool.submit(task);
        }
//        Future f = pool.submit(task);
//        f.
        ((ThreadPoolExecutor) pool).allowCoreThreadTimeOut(true);
    }
}
