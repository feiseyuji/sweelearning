package com.womenhz.swee.data.list;

import lombok.Data;

@Data
public class MyList<T> {

    private Node head;

    private Node tail;

    private Integer size;

    public MyList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void insert(T data) {
        //System.out.println("----");
        Node current = this.head;
        if (current == null) {
           this.head = new Node(data);
           return;
        }
        //System.out.println("cur"+current);
        Node pre = current;
        while (current != null) {
           //if (current.getNext() != null) {
               pre = current;
               current = current.getNext();
           //}
        }

        pre.setNext(new Node(data));
        //System.out.println("cur1"+pre);
    }
    /**
     * 1 -> 2 -> 3 -> 5 -> null
     * cur  next   temp
     * next.next = cur
     *
     *
     * */
    public void reverse() {
        Node cur = this.head;
        if (cur == null) {
            return;
        }

        System.out.println("cur= "+cur);
        Node pre = null;
        while (cur != null) {
            System.out.println("cur1= "+cur);
            Node next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }

        this.head = pre;
    }

    public void display() {
        //System.out.println("----");
        Node cur = head;
        if (cur == null) {
            return;
        }

        while (cur != null) {
            System.out.println(cur.getData());
            cur = cur.getNext();
        }
    }

    public static void main(String[] args) {
        MyList<Integer> myList = new MyList<>();

        myList.insert(1);
        myList.insert(2);
        myList.insert(3);
        myList.insert(5);

       myList.display();
       myList.reverse();
       System.out.println("===============");
       myList.display();
    }


}
