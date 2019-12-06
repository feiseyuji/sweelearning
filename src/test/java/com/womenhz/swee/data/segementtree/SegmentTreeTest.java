package com.womenhz.swee.data.segementtree;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SegmentTreeTest {


    public static void main(String[] args) {

        Integer[] arr = {-2, 0,3,-5,2,-1};

        SegmentTree<Integer> segmentTree = new SegmentTree<>(arr,(x, y) -> x + y);
        log.info("set = "+segmentTree.getTree());
        Object[] tree= segmentTree.getTree();
        int len = tree.length;
        for (int i = 0; i < len; i++ ) {
            log.info(i+" "+tree[i]);
        }

        log.info(segmentTree.select(0, 2));

        segmentTree.set(0, 1);

        log.info(segmentTree.select(0, 2));

    }

}
