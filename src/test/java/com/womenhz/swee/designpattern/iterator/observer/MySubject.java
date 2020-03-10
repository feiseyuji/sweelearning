package com.womenhz.swee.designpattern.iterator.observer;

import java.util.List;
import com.google.common.collect.Lists;

public class MySubject implements Subject {

    List<Observer> observerList = Lists.newArrayList();

    void attach(Observer observer) {
        observerList.add(observer);
    }

    void deattach(Observer observer) {
        observerList.remove(observer);
    }

    void update() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }

}
