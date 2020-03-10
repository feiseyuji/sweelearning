package com.womenhz.swee.current.threadpool;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import com.google.common.collect.Lists;

public class SimpleThreadPool {

    private final int size;

    private final static int DEFAULT_SIZE = 10;

    private final int queueSize;

    private final static int DEFAULT_QUEUE_SISE = 2000;

    private final static String THREAD_PREFIX = "simple_thread_pool- ";

    private final static ThreadGroup GROUP = new ThreadGroup("thread_group");
    private volatile int seq = 0;

    private final static LinkedList<Runnable> TASK_QUEUE  = Lists.newLinkedList();

    private final static List<WorkTask> THREAD_QUEUE = Lists.newArrayList();

    private final DiscardPolicy discardPolicy;

    private volatile boolean distroy = false;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("discard this task");
    };

    public SimpleThreadPool(){
        this(DEFAULT_SIZE, DEFAULT_QUEUE_SISE ,DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int size, int queueSize, DiscardPolicy discardPolicy){ this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    private void init(){
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }
    }

    public void submit(Runnable runnable) {
        if (distroy) {
            throw new IllegalStateException("thread pool is illegal state");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public boolean distory() {
        return distroy;
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void shutDown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }
        int threadSize = THREAD_QUEUE.size();
        while (threadSize > 0) {
            for (WorkTask workTask : THREAD_QUEUE) {
                if (workTask.taskState == TaskState.BLOCKED) {
                    workTask.interrupt();
                    workTask.close();
                    threadSize--;
                }else {
                    Thread.sleep(10);
                }
            }
        }

        this.distroy = true;
        System.out.println(" the pool is distory");
    }

    private void createWorkTask() {
        WorkTask task = new WorkTask(GROUP, THREAD_PREFIX+(seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    private enum TaskState {
        FREE, RUNNING,BLOCKED, DEAD;
    }
    private static class WorkTask extends Thread{
        private volatile TaskState taskState = TaskState.FREE;

        public WorkTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                           break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40).forEach(i -> simpleThreadPool.submit(() -> {
            System.out.println("init... "+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over... "+Thread.currentThread().getName());
        }));

        Thread.sleep(10_000);
        simpleThreadPool.shutDown();
        simpleThreadPool.submit(() -> {
            System.out.println("===============");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);//new LinkedBlockingQueue<Runnable>()
        Executors.newCachedThreadPool();// new SynchronousQueue<Runnable>()
        Executors.newScheduledThreadPool(12);// new DelayedWorkQueue()
        Executors.newSingleThreadExecutor();//new LinkedBlockingQueue<Runnable>())
        executorService.submit(() -> {
            System.out.println("jj");
        });
    }

}
