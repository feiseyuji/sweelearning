package com.womenhz.swee.data.tree.avltree;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AVLTree<K extends Comparable<K>, V> {

    private Node root;

    private int size;

    @Data
    private class Node {

        private K k;

        private V v;

        private Node left, right;

        private int height;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;

            left = null;
            right = null;

            height = 1;
        }
    }

    /**
     *
     *     6
     *   /   \
     *  4     7
     * / \
     *2   5
     *
     * */
    public int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public void add(K k, V v) {
        root = add(root, k, v);
    }

    private Node add(Node node, K k, V v) {
        if (node == null) {
            node = new Node(k, v);
            size++;
            return node;
        }
        if (node.k.compareTo(k) > 0) {//left
            node.left = add(node.left, k, v);
        }else if (node.k.compareTo(k) < 0) {//right
            node.right = add(node.right, k, v);
        }else {
            node.v = v;
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            log.info("factor= "+balanceFactor);
        }

        //旋转平衡
        //RR
        if (balanceFactor > 1 && getBalanceFactor(node.left) > 0) {
            return rightRotate(node);
        }

        //LL
        if (balanceFactor < -1 && getBalanceFactor(node.right) < 0) {
            return leftRotate(node);
        }

        //LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public void set(K k, V v) {
        Node node = getNode(root, k);
        if (node == null) {
            log.info("the k is not exist");
            return ;
        }
        node.v = v;
    }

    public Node getNode(K k) {
        return getNode(root, k);
    }

    private Node getNode(Node node, K k) {
        if (node == null) {
            return node;
        }

        if (node.k.compareTo(k) > 0) {//left
            return getNode(node.left, k);
        }else if(node.k.compareTo(k) < 0){//right
            return getNode(node.right, k);
        }else {
            return node;
        }
    }

    public boolean contains(K k) {
        return getNode(root, k) != null;
    }

    public Node minNode() {
        return minNode(root);
    }

    private Node minNode(Node node) {
        if (node.left == null) {
            return node;
        }
        return minNode(node.left);
    }

    private Node maxNode(Node node) {
        if (node.right == null) {
            return node;
        }
        return maxNode(node.right);
    }

    public Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            size--;
            node.right = null;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }
    public void remove(K k) {
        remove(root, k);
    }

    private Node remove(Node node, K k) {
        if (node == null) {
            return node;
        }

        if (node.k.compareTo(k) > 0) {//left
            node.left = remove(node.left, k);
            return node;
        }else if (node.k.compareTo(k) < 0) {//right
            node.right = remove(node.right, k);
            return node;
        }else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            Node minNode = minNode(node.right);
            Node successor = removeMin(node.right);

            minNode.left = node.left;
            minNode.right = successor;

            node.left = null;
            node.right = null;

            return minNode;
        }
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    public Node rightRotate(Node y) {
        Node x = y.left;
        Node t3 = x.right;

        x.right = y;
        y.left = t3;

        y.height = Math.max(getHeight(y.right), getHeight(y.left)) + 1;
        x.height = Math.max(getHeight(x.right), getHeight(x.left)) + 1;

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4

    public Node leftRotate(Node y) {
        Node x = y.right;
        Node t2 = x.left;

        x.left = y;
        y.right = t2;

        y.height = Math.max(getHeight(y.right), getHeight(y.left)) + 1;
        x.height = Math.max(getHeight(x.right), getHeight(x.left)) + 1;

        return x;
    }

    //高度为h最小节点的avl树  s(h) = s(h - 1) + s(h - 2)
    // 1. 对y的左儿子的左子树进行插入

    // 2. 对y的左儿子的右子树进行插入
    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x                T3
    //       / \                           /   \             /  \
    //      x   T4     向右旋转 (y)       z     y   --->    x    y
    //     / \       - - - - - - - ->          / \         /    /  \
    //    z   T3                              T3  T4      z    T1   T4
    //          \                            /
    //           T1                         T1

    // 3. 对y的右儿子的左子树进行插入

    // 4. 对y的右儿子的右子树进行插入
    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x                  T2
    //  /  \                          /   \              /   \
    // T1   x      向左旋转 (y)      y     z            y     x
    //     / \   - - - - - - - ->   / \       --->     /  \     \
    //   T2   z                    T1  T2            T1    T3    z
    //  /                                \
    // T3                                 T3
}
