package com.womenhz.swee.current.concurrentpattern;

import java.util.Random;

public class GuardDemo {

    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();

        Client client = new Client(requestQueue, "hello", 1000);
        Client client1 = new Client(requestQueue, "hello1", 1000);
        Client client2 = new Client(requestQueue, "hello2", 1000);
        client.start();
        client1.start();
        client2.start();

        Server server = new Server(requestQueue, "hi", 1000);
        Server server1 = new Server(requestQueue, "hi1", 1000);

        server.start();
        server1.start();
    }

}

class Client extends Thread {//保存请求

    private String name;

    private Random random;

    private RequestQueue requestQueue;

    public Client(RequestQueue requestQueue,String name, long times) {
        this.name = name;
        this.requestQueue = requestQueue;
        this.random = new Random(times);
    }
    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
            requestQueue.put(new Request("no + "+i));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Server extends Thread {

    private String name;

    private Random random;

    private RequestQueue requestQueue;

    public Server(RequestQueue requestQueue,String name, long times) {
        this.name = name;
        this.requestQueue = requestQueue;
        this.random = new Random(times);
    }
    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getName()+" deal "+requestQueue.getRequest());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}