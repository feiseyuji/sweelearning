package com.womenhz.swee.designpattern.iterator.observer;

public class MyObserver implements Observer {

    @Override
    public void update() {
        System.out.println("收到");
    }
}
