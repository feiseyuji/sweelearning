package com.womenhz.swee.data.queue;

import com.womenhz.swee.data.heap.MaxHeap;

public class PriorityOueue<E extends Comparable<E>> implements Queue<E> {

    private MaxHeap<E> maxHeap;

    public PriorityOueue() {
        maxHeap = new MaxHeap<>();
    }

    @Override
    public void push(E e) {
        maxHeap.add(e);
    }

    @Override
    public E pop() {
        return maxHeap.fetchMax();
    }

    @Override
    public E getFront() {
        return maxHeap.findMax();
    }
}
