package com.womenhz.swee.current.automic.integer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IntegerLockTest {

    private static final AtomicIntegerLock lock = new AtomicIntegerLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        lock.tryLock();
                        doSomeThing();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }.start();
        }
    }

    public static void doSomeThing() throws InterruptedException {
        Thread.sleep(100000);
    }

}
