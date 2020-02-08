package com.womenhz.swee.current.threadpool;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcutorServiceSample1 {

    public static void main(String[] args) throws Exception {

        testInvokeAny();
    }

    private static void testInvokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList = IntStream.range(0, 5).boxed().map(i -> (Callable<Integer>)() -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            System.out.println(Thread.currentThread().getName()+" : "+i);
            return i;
        }).collect(Collectors.toList());

        System.out.println(executorService.invokeAny(callableList));
    }

}
