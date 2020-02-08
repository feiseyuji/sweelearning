package com.womenhz.swee.data.queue;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class QueueTest {

    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityOueue<>();

        queue.push(2);
        queue.push(5);
        queue.push(3);

       log.info("queuq= "+queue);

        queue.pop();

        log.info("queuq1= "+queue);
    }
}
