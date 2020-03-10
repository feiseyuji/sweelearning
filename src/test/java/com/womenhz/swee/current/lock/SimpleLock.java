package com.womenhz.swee.current.lock;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import com.google.common.collect.Lists;

public class SimpleLock implements Lock {

    private boolean initValue;

    private Collection<Thread> blockThreads = Lists.newArrayList();

    private Thread concurrentThread;

    public SimpleLock() {
        this.initValue = false;
    }


    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue){
            blockThreads.add(Thread.currentThread());
            this.wait();
        }
        blockThreads.remove(Thread.currentThread());
        concurrentThread = Thread.currentThread();
        initValue = true;
    }

    @Override
    public synchronized void lock(long time) throws InterruptedException, TimeoutException {
        if (time <= 0){
            lock();
        }
        long remain = time;
        long endTime = System.currentTimeMillis() + time;
        while (initValue) {
            if (remain <= 0) {
                throw new TimeoutException("Time out");
            }
            blockThreads.add(Thread.currentThread());
            this.wait(time);
            remain = endTime - System.currentTimeMillis();
        }
        initValue = true;
        concurrentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if (concurrentThread == Thread.currentThread()) {
            initValue = false;
            Optional.of(Thread.currentThread().getName()+" release lock").ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockCollection() {
        return Collections.unmodifiableCollection(blockThreads);
    }

    @Override
    public int getBlockSize() {
        return blockThreads.size();
    }
}
