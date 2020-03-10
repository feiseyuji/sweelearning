package com.womenhz.swee.current.lock;

import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class SimpleLockDemo {

    public static void main(String[] args) throws InterruptedException {

        final SimpleLock simpleLock = new SimpleLock();

        Stream.of("T1", "T2","T3", "T4").forEach(name -> new Thread(() -> {
            try {
                simpleLock.lock(1000);
                Optional.of(Thread.currentThread().getName()+"get lock").ifPresent(System.out::println);
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println(Thread.currentThread().getName()+" "+e.getMessage());
            } finally {
                simpleLock.unlock();
            }
        },name).start());

        Thread.sleep(10);
        simpleLock.unlock();

    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName()+"is working...").ifPresent(System.out::println);
        Thread.sleep(10_000);
    }

}
