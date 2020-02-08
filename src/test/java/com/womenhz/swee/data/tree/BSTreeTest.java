package com.womenhz.swee.data.tree;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BSTreeTest {

    public static void main(String[] args) {
        BSTree bsTree = new BSTree();
        bsTree.add(6);
        bsTree.add(2);
        bsTree.add(7);
        bsTree.add(4);
        bsTree.add(1);

        bsTree.preTraversalNoRecursion();

        log.info("root= "+bsTree.getRoot());
        bsTree.midTraversal();
        log.info("min= "+bsTree.getMinNode(bsTree.getRoot()));
        bsTree.delete(2);
        log.info("delete 2= "+bsTree.getRoot());
        bsTree.delete(1);
        log.info("delete 1= "+bsTree.getRoot());
    }
}
