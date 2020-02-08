package com.womenhz.swee.data.queue;


public interface Queue<E> {

    void push(E e);

    E pop();

    E getFront();
}
