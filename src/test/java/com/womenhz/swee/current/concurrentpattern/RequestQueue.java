package com.womenhz.swee.current.concurrentpattern;

import java.util.LinkedList;
import java.util.List;
import com.google.common.collect.Lists;

public class RequestQueue {

    LinkedList<Request> requests = Lists.newLinkedList();

    public synchronized Request getRequest() {
        while (requests.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return requests.removeFirst();
    }

    public synchronized void put(Request request) {
        requests.addLast(request);
        notifyAll();
    }

}

class Request {
    private final String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

