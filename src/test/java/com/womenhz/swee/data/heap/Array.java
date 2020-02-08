package com.womenhz.swee.data.heap;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class Array<E> {

    private E[] data;

    private int size;

    public Array(int capacity) {
        data = (E[])new Object[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    public void add(int index, E e) {
        if (index < 0 || index > data.length) {
            log.info("the index illegal");
            return;
        }

        if (size == data.length) {
            resize(2 * size);
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = e;
        size++;

    }

    public void addLast(E e) {
        add(size, e);
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void add(E e) {
        if (size == data.length) {
            resize(2 * size);
        }
        data[size] = e;
        size++;
    }

    public E remove(int index) {
        if (index < 0 && index >= size) {
            log.info("illegal index");
            return null;
        }

        E ret = data[index];

        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        log.info("i = "+index+" size= "+size);
        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);

        return ret;
    }

    public void resize(int capacity) {
        E[] newData = (E[]) new Object[capacity];
        //int size = Math.min(data.length, capacity);
        for (int i = 0; i < size; i++) {
            log.info("i = "+i);
             newData[i] = data[i];
        }
        data = newData;
    }

    public void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public E get(int k) {
        if (k < 0 || k > size - 1) {
            throw new IllegalArgumentException("index is out of arr");
        }
        return data[k];
    }

}
