package com.womenhz.swee.current.leecode;

import com.google.common.util.concurrent.Runnables;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrintOrderDemo {

    private static Foo foo;

    public PrintOrderDemo(Foo foo) {
        this.foo = foo;
    }

    public static void main(String[] args) throws InterruptedException {

        int[] order = {1, 2, 3};

        PrintOrderDemo printOrderDemo = new PrintOrderDemo(new Foo());
        printOrderDemo.printOrder(order);


    }

    public void printOrder(int[] order) throws InterruptedException {
        String resultStr = "";
        for (int i = 0; i < order.length ; i++) {
            int temp = i + 1;
            if (temp == 1){
                foo.first(() -> {
                    printOrder1(order, 1);
                });
            }


            if (temp == 2) {
                foo.second(() -> {
                    printOrder1(order, 2);
                });
            }


            if (temp == 3) {
                foo.third(() -> {
                    printOrder1(order, 3);
                });
            }

        }
    }


    private String printOrder1(int[] order, int index) {
        int preIndex = index - 1;
        if (order[preIndex] == 1){
            if (index == 1) {
                System.out.println("first");
                return "first";
            }
            if (index == 2){
                System.out.println("second");
                return "second";
            }


            if (index == 3){
                System.out.println("third");
                return "third";
            }

        }

        if (order[preIndex] == 2){//线程no
            if (index == 1) {
                System.out.println("first");
                return "first";
            }
            if (index == 2){
                System.out.println("second");
                return "second";
            }


            if (index == 3){
                System.out.println("third");
                return "third";
            }
        }

        if (order[preIndex] == 3){
            if (index == 1) {
                System.out.println("first");
                return "first";
            }
            if (index == 2){
                System.out.println("second");
                return "second";
            }


            if (index == 3){
                System.out.println("third");
                return "third";
            }
        }
        return "";
    }

}

class FirstPrint implements Runnable {

    private Foo foo;

    public FirstPrint(Foo foo) {
        this.foo = foo;
    }
    @Override
    public void run() {
       // foo.;
    }

    private void firstPrint() {

    }
}

class Foo {

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

}
