package com.womenhz.swee.current.threadpool;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPooltestTask {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(10,
                20,
                30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                r -> { Thread t = new Thread(r);return t; },
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("thread pool done");

        IntStream.range(1, 20).boxed().forEach(i ->{
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("--"+Thread.currentThread().getName()+ i +" doned");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        //executorService.shutdown();
        try{
            List<Runnable> runnables = executorService.shutdownNow();
            System.out.println("-- "+runnables);
            System.out.println("--- "+runnables.size());
        }catch (Exception e) {
           e.printStackTrace();
        }
    }
}
