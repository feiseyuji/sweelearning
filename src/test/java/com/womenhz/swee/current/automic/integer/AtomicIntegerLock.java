package com.womenhz.swee.current.automic.integer;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AtomicIntegerLock {

    private static final AtomicInteger lock = new AtomicInteger(0);

    private Thread thread;

    public void tryLock() {
        boolean success = lock.compareAndSet(0, 1);
        if (!success) {
            log.info("get the lock fail");
        }
    }

    public void unlock() {
        if (1 == lock.get())
           lock.compareAndSet(1, 0);

    }

}
