package com.womenhz.swee.current.lock;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface Lock {

     void lock() throws InterruptedException;

     void lock(long time) throws InterruptedException, TimeoutException;

     void unlock();

     Collection<Thread> getBlockCollection();

     int getBlockSize();
}
