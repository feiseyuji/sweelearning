package com.womenhz.swee.current.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.persistence.criteria.CriteriaBuilder.In;
import ch.qos.logback.classic.pattern.SyslogStartConverter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestShutdown {
    public static void main(String[] args) throws InterruptedException {
        //isShutDown();
        //exceptionsHansndleTask();
        log.info(Integer.SIZE);
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY = (1 << COUNT_BITS) - 1;
        int RUNNING = -1 << COUNT_BITS;        // 11100000 00000000 00000000 00000000
        int SHUTDOWN = 0 << COUNT_BITS;        // 00000000 00000000 00000000 00000000
        int STOP = 1 << COUNT_BITS;            // 00100000 00000000 00000000 00000000
        int TIDYING = 2 << COUNT_BITS;         // 01000000 00000000 00000000 00000000
        int TERMINATED = 3 << COUNT_BITS;

        log.info("ca= "+CAPACITY);
        log.info("run = "+RUNNING);
        log.info("shut= "+SHUTDOWN);
        log.info("stop= "+STOP);
        log.info("tid= "+TIDYING);
        log.info("Ter= "+TERMINATED);

    }

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }


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
