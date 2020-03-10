package com.womenhz.swee.current.lock;

import java.util.Optional;

public class TryLockDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();



        Thread t2 = new Thread(() -> {
            try {
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2");

        t2.start();
        Thread.sleep(2000);
        t2.interrupt();
        System.out.println(t2.isInterrupted());

    }

    private static synchronized void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName()+"run ...").ifPresent(System.out::println);
        while (true) {

        }
        //Thread.sleep(10_000);
    }

}
