package com.womenhz.swee.current.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import ch.qos.logback.classic.pattern.SyslogStartConverter;

public class TestShutdown {
    public static void main(String[] args) throws InterruptedException {
        //isShutDown();
        exceptionsHansndleTask();
    }

    private static void isShutDown() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(executorService.isShutdown());
        executorService.shutdown();
        System.out.println(executorService.isShutdown());

        executorService.execute(() -> System.out.println("new task"));
    }

    private static void exceptionsHansndleTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1, new MyThreadFactory());

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        IntStream.range(0, 10).forEach(i -> {
            executorService.execute(() -> System.out.println(i / 0));
        });

        System.out.println(".... end");

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        System.out.println("-------------------------");
    }

    private static  class MyThreadFactory implements ThreadFactory {
        private final static AtomicInteger SEQ = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("My- Thread- "+SEQ.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, cause) -> {
                System.out.println("the thread "+t.getName() + " execute failed. ");
                cause.printStackTrace();
                System.out.println("===============");
            });
            return thread;
        }
    }
}
