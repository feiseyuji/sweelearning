package com.womenhz.swee.data.heap;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap() {
        data = new Array<>();
    }

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public int leftChild(int index) {
        return 2 * index + 1;
    }

    public int rightChild(int index) {
        return 2 * index + 2;
    }

    public int parent(int index) {
        return (index - 1) / 2;
    }

    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int i) {
        while (i > 0 && data.get(parent(i)).compareTo(data.get(i)) < 0) {
            data.swap(parent(i), i);
            i = parent(i);
        }
    }

    public E findMax() {
        return data.get(0);
    }

    public E fetchMax() {
        E e = data.get(0);
        data.swap(0, data.getSize() -1);
        data.remove(data.getSize() -1);

        siftDown(0);
        return e;
    }

    private void siftDown(int i) {
        while (leftChild(i) < data.getSize()) {
            int k = leftChild(i);
            if (k + 1 < data.getSize() && data.get(k + 1).compareTo(data.get(k)) > 0) {
                k++;
            }

            if (data.get(i).compareTo(data.get(k)) > 0) {
                break;
            }
            data.swap(i, k);

            i = k;
        }
    }

    public E  replaceMax(E e) {
        E ret = fetchMax();
        data.add(0, e);
        siftDown(0);
        return ret;
    }

}
