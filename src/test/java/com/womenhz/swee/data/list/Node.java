package com.womenhz.swee.data.list;

import lombok.Data;

@Data
public class Node<T> {

    private Node next;

    private Node pre;

    private T data;


    public Node(T data) {
        this.next = null;
        this.pre = null;
        this.data = data;
    }
}
