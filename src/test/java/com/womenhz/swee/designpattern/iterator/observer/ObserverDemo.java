package com.womenhz.swee.designpattern.iterator.observer;

public class ObserverDemo {

    public static void main(String[] args) {
        Observer observer1 = new MyObserver();
        Observer observer2 = new MyObserver();

        Subject subject = new MySubject();

        ((MySubject) subject).attach(observer1);
        ((MySubject) subject).attach(observer2);

        ((MySubject) subject).update();
    }
}
