package com.womenhz.swee.data.list;

import io.swagger.models.auth.In;
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
    /**
     *
     * while(node1 != null) {
     *     if（node2 != null）{
     *         int da
     *     }
     *
     * }
     *
     *
     *
     *
     * **/
    public Node add2Num(Node<Integer> node1, Node<Integer> node2){
        Node head = null;
        Node pre = head;
        Node cur = head;
        boolean isHead = true;
        boolean gt10 = false;
        while (node1 != null) {
            if (node2 != null) {
                int data = !gt10 ? node1.getData() + node2.getData() : node1.getData() + node2.getData() + 1;
                if (isHead) {
                    if (data > 9) {
                        cur = new Node<>(data - 10);
                    }else {
                        cur = new Node(data);
                    }
                    head = cur;
                    pre = cur;
                    isHead = false;
                }
                if (data > 9) {
                    cur = new Node<>(data - 10);
                }else {
                    cur = new Node(data);
                }
                pre.setNext(cur);
                pre = cur;
                node2 = node2.getNext();
            }else {
                pre.setNext(node1);
            }
            node1 = node1.getNext();
        }

        if (node2 != null) {
            pre.setNext(node2);
        }
        return head;
    }
    public Node add2Nums(Node<Integer> node1, Node<Integer> node2) {
        Node head = new Node(-1);
        Node cur = head;
        int extra = 0;
        while (node1 != null || node2 != null || extra != 0) {
            int value1 = node1 != null ? node1.getData() : 0;
            int value2 = node2 != null ? node2.getData() : 0;

            int sumValue = value1 + value2 + extra;
            extra = sumValue / 10;

            cur.setNext(new Node(sumValue % 10));

            node1 = node1 != null ? node1.getNext() : null;
            node2 = node2 != null ? node2.getNext() : null;

            cur = cur.getNext();
        }
        return head.getNext();
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
