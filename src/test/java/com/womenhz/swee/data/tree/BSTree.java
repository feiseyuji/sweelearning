package com.womenhz.swee.data.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class BSTree {

    private Node root;

    @Data
    static class Node {

        private int data;

        private Node left;

        private Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BSTree() {
        super();
    }

    public void add(int data) {
        root = add(root, data);
    }

    public void preTraversalNoRecursion() {
        preTraversalNoRecursion(root);
    }

    private void preTraversalNoRecursion(Node node) {
        if (node == null) {
            log.info("there is no data");
            return;
        }

        Stack<Node> stack = new Stack();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            log.info("node = "+node.getData());

            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 层序遍历
     * */
    public void levalTraversal() {
        if (root == null) {
            log.info("there is no data");
            return;
        }

        Queue<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            log.info("node = "+current.getData());

            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    /**
     *           6
     *         /   \
     *        2      7
     *       /  \
     *       1  4
     *
     * */
    private Node add(Node node, int data) {
        if (node == null) {
            node = new Node(data);
            return node;
        }
        if (node.data > data) {//left
            Node left = add(node.left, data);
            node.left = left;
            return node;
        }else if (node.data < data) {//right
            Node right = add(node.right, data);
            node.right = right;
            return node;
        }
        return null;
    }

    public void delete(int data) {
        delete(root, data);
    }

    private Node delete(Node node, int data) {
        if (node == null) {
            log.info("data is not in this tree.");
            return node;
        }

        if (node.getData() > data) {//left
            node.left = delete(node.left, data);
            return node;
        } else if (node.getData() < data ) {
            node.right = delete(node.right, data);
            return node;
        }else {// finded data

            if (node.getLeft() == null) {
                Node rightNode = node.right;
                node.right = null;
                return rightNode;
            }

            if (node.getRight() == null) {
                Node leftNode = node.left;
                node.left = null;
                return leftNode;
            }

            //左右都不为空
            Node successor = getMinNode(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;

            return successor;
        }

    }

    public void removeMin(){
        root = removeMin(root);
    }

    private Node removeMin(Node node){
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

    public void removeMax() {
        root = removeMax(root);
    }

    private Node removeMax(Node node) {
        if (node == null) {
            return node;
        }

        if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        }

        if (node.left != null) {
            Node leftNode = node.left;
            node.left = null;
            return leftNode;
        }
        return null;
    }

    public Node getMinNode(Node node){
        if (node == null) {
            return node;
        }

        if (node.left != null) {
            return getMinNode(node.left);
        }

        if (node.left == null) {
            return node;
        }
        return null;
    }

     public void midTraversal() {
         midTraversal(root);
     }

     private void midTraversal(Node node) {
         if (node == null) {
             return;
         }
        midTraversal(node.left);
        System.out.println("node= "+node.getData());
        midTraversal(node.right);
     }


}
