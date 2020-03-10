package com.womenhz.swee.data.tree.rbtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Data;

public class RBTree<K extends Comparable<K>, V> {

    private final static boolean RED = true;

    private final static boolean BLACK = false;

    @Data
    private class Node {

        private K key;

        private V value;

        private Node left, right;

        private boolean color;

        public Node(K k, V v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
            this.color = RED;
        }
    }

    private Node root;

    private int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    public boolean isRed(Node node) {
        if (node == null) {
            return BLACK;
        }
        return node.color;
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate (Node node) {
        Node x = node.right;

        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;
        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node) {
        Node x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.right.color = BLACK;
        node.left.color = BLACK;
    }
    
    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        }else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        }else {
            node.value = value;
        }

        return node;
    }

    /****
     * 1. add
     * 2. rotate
     * 3. delete
     * 4. delay
     *
     * */
    private List<Integer> list = new ArrayList<>();

    List<Integer> list1 = new LinkedList<>();

    Map<String, String> map  = new LinkedHashMap<>();//new HashMap<>();

}
