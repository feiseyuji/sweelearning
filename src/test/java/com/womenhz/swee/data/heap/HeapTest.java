package com.womenhz.swee.data.heap;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HeapTest {

    public static void main(String[] args) {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        maxHeap.add(1);
        maxHeap.add(4);
        maxHeap.add(3);
        maxHeap.add(6);
        maxHeap.add(5);


        log.info("heap= "+maxHeap);

        maxHeap.fetchMax();

        log.info("heap1 = "+maxHeap);

        maxHeap.replaceMax(9);

        log.info("heap2 = "+maxHeap);
    }
}
