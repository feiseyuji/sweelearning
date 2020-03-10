package com.womenhz.swee.current.leecode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import com.womenhz.swee.designpattern.iterator.observer.Subject;

public class PrintRoundDemo {

    public static void main(String[] args) {

        PrintFoo printFoo = new PrintFoo();
       //Thread t1 = new Thread(new PrintLoopA(printFoo, 5), "AA");
       //Thread t2 = new Thread(new PrintLoopB(printFoo, 5), "BB");
       //Thread t3 = new Thread(new PrintLoopC(printFoo, 5), "CC");


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printFoo.printA(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printFoo.printB(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printFoo.printC(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("========================================= "+i);
            }
        }, "CC").start();


    }

}

/**
 * 3个线程 a print 5个a  b 5 b c 5c  5loop
 *
 * */

class PrintFoo {

    private Lock lock = new ReentrantLock();

    private Condition conditionA = lock.newCondition();

    private Condition conditionB = lock.newCondition();

    private Condition conditionC = lock.newCondition();

    private int number = 1;


    public void printA(int time) throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                conditionA.await();
            }
                for (int i = 0; i < time ; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+i + "A");
                }
                number = 2;
                conditionB.signal();
        }finally {
            lock.unlock();

        }
    }

    public void printB(int time) throws InterruptedException {
        lock.lock();
        try {
            while (number != 2) {
                conditionB.await();
            }
                for (int i = 0; i < time ; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+i + "B");
                }
                number = 3;
                conditionC.signal();
        }finally {
            lock.unlock();

        }
    }

    public void printC(int time) throws InterruptedException {
        lock.lock();
        try {
            while (number != 3) {
                conditionC.await();
            }
                for (int i = 0; i < time ; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+i + "C");
                }
                number = 1;
                conditionA.signal();

        }finally {
            lock.unlock();
        }
    }
}
