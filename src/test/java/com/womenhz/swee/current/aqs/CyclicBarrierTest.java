package com.womenhz.swee.current.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {

        int N = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, () -> {
           System.out.println("准备完毕， run ...");
        });

        //List<Thread> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Thread t = new Thread(new PrepareWork(cyclicBarrier), "p ["+i+"]");
            t.start();
            if (i == 3) {
                t.interrupt();
            }
        }

        Thread.sleep(2000);
        System.out.println("Barrier是否损坏：" + cyclicBarrier.isBroken());
    }

}
