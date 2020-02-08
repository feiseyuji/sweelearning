package com.womenhz.swee.current.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp +"", temp+"");
            },""+i).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp +"");
            },""+i).start();
        }
    }


}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object val) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 准备写入");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, val);
            System.out.println(Thread.currentThread().getName()+" 写入完成");
        }catch (Exception e) {

        }finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 准备读");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.get(key);
            System.out.println(Thread.currentThread().getName()+" 都完成");
        }catch (Exception e) {

        }finally {
            rwLock.readLock().unlock();
        }

    }
}
