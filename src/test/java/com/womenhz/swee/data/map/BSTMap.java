package com.womenhz.swee.data.map;

import lombok.Data;

@Data
public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {

    private Node root;

    private int size;

    @Data
    private class Node {

        private K k;

        private V v;

        private Node left, right;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    @Override
    public void put(K k, V v) {
        root = put(root, k, v);
    }

    private Node put(Node node, K k, V v) {
        if (node == null) {
            return new Node(k, v);
        }

        if (k.compareTo(node.getK()) < 0) {
            node.left = put(node.left, k, v);
        }else if (k.compareTo(node.getK()) > 0) {
            node.right = put(node.right, k, v);
        }else {
            node.setV(v);
        }

        return node;
    }

    @Override
    public V get(K k) {
        Node node = get(root, k);
        if (node != null) {
            return node.getV();
        }
        return null;
    }

    private Node get(Node node, K k) {
        if (node == null) {
            return null;
        }
        if (k.compareTo(node.getK()) < 0) {
            return get(node.left, k);
        }else if(k.compareTo(node.getK()) > 0) {
            return get(node.right, k);
        }else {
            return node;
        }
    }

    @Override
    public void remove(K k) {
        remove(root, k);

    }

    private Node remove(Node node, K k) {
        if (node == null) {
            return node;
        }

        if (k.compareTo(node.getK()) < 0) {
            node.left = remove(node.left,k);
            return node;
        }else if (k.compareTo(node.getK()) > 0) {
            node.right = remove(node.right, k);
            return node;
        }else {

            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                return leftNode;
            }

            Node minNode = getMinNode(node);
            minNode.right = removeMin(node.right);

            minNode.left = node.left;

            node.left = node.right = null;
            return minNode;
        }
    }

    public Node getMinNode(){
        return getMinNode(root);

    }

    private Node getMinNode(Node node) {
        if (node == null) {
            return node;
        }
        if (node.left != null) {
            return getMinNode(node.left);
        }
        return node;
    }

    public Node getMaxNode() {
        return getMaxNode(root);
    }

    private Node getMaxNode(Node node) {
        if (node == null) {
            return node;
        }
        if (node.right != null) {
            return getMinNode(node.right);
        }
        return node;
    }

    public Node removeMin() {
        return removeMin(root);
    }

    private Node removeMin(Node node) {
       if (node == null) {
           return node;
       }

       if (node.left != null) {
           node.left = removeMin(node.left);
           return node;
       }

       if (node.right != null) {
           Node rightNode = node.right;
           node.right = null;
           return rightNode;
       }

       return null;
    }

    public Node romoveMaxNode() {
        return removeMaxNode(root);
    }

    private Node removeMaxNode(Node node) {
        if (node == null) {
            return node;
        }

        if (node.right != null) {
            node.right = removeMaxNode(node.right);
            return node;
        }

        if (node.left != null) {
            Node leftNode = node.left;
            node.left = null;
            return leftNode;
        }
        return null;
    }

}
