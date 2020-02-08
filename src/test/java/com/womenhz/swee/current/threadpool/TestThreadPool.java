package com.womenhz.swee.current.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)buildThreadPool();
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executorService.getActiveCount()
                    || queueSize != executorService.getQueue().size()) {
                System.out.println(executorService.getActiveCount());
                System.out.println(executorService.getCorePoolSize());
                System.out.println(executorService.getQueue().size());
                System.out.println(executorService.getMaximumPoolSize());
                activeCount = executorService.getActiveCount();
                queueSize = executorService.getQueue().size();

                System.out.println("-==================================");
            }
        }
    }
    private static void sleepSeconds(long seconds) {
        try {
            System.out.println("* "+Thread.currentThread().getName()+" *");
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * 1. coreSize = 1, MaxSize = 2 , blockqueueSize = is empty ,task = 3
     * 2. coreSize = 1, MaxSize = 2, blockQueue size = 5  , task = 7
     * 3. coreSize = 1, MaxSize = 2, blockQueue size = 5  , task = 8
     *
     *
     * */
    private static ExecutorService buildThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
                return t;
        }, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("thread pool done");

        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        //executorService.execute(() -> sleepSeconds(100));
        return executorService;

    }

}
