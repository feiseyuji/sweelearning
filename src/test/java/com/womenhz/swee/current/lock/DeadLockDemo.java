package com.womenhz.swee.current.lock;

import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new DeadLock(lockA, lockB), "AAA").start();
        new Thread(new DeadLock(lockB, lockA), "BBB").start();
    }

}

class DeadLock implements Runnable {

    private String lockA;

    private String lockB;

    public DeadLock(String lockA, String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName()+" hold "+lockA+ " try "+lockB);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName()+" hold "+lockB+ " try "+lockA);
            }
        }
    }
}
