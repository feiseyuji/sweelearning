package com.womenhz.swee.current.automic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AtomicIntegerTest {

    //static volatile Integer value = 0;

    static AtomicInteger value = new AtomicInteger(0);

    static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++ ) {
                    int v = value.getAndIncrement();
                    set.add(v);
                    log.info(Thread.currentThread().getName()+" : "+v);
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++ ) {
                    int v = value.getAndIncrement();
                    set.add(v);
                    log.info(Thread.currentThread().getName()+" : "+v);
                }
            }
        };

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++ ) {
                    int v = value.getAndIncrement();
                    set.add(v);
                    log.info(Thread.currentThread().getName()+" : "+v);
                }
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++ ) {
                    int v = value.getAndIncrement();
                    set.add(v);
                    log.info(Thread.currentThread().getName()+" : "+v);
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        log.info(set.size());

    }

    public static void incr() {
        value.getAndIncrement();
    }

}
