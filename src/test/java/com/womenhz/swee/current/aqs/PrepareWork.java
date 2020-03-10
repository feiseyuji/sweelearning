package com.womenhz.swee.current.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class PrepareWork implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public PrepareWork(CyclicBarrier cyclicBarrier) {
      this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName()+": ready");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+": interupt");
        } catch (BrokenBarrierException e) {
            System.out.println(Thread.currentThread().getName()+": 抛出");
        }
    }
}
