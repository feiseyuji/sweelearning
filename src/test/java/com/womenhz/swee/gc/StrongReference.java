package com.womenhz.swee.gc;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class StrongReference {

    public static void main(String[] args) throws InterruptedException {
//        Object object1 = new Object();
//        Object object2 = object1;
//        object1 = null;
//        System.gc();
//        System.out.println(object2);
        //soft_Memory_NotEnough();
        phantomReference();
    }

    //强引用，普通java引用，强引用永远不会被gc
    //软引用，SoftReference, 当内存充足时不会被gc，当内存不足时会被gc
    //弱引用，WeakReference, 不管内存是否充足，都会被gc
    //虚引用，PhantomReference ,和引用队列配合使用，加入引用队列之后，响应的空间被回收

    public static void soft_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        }catch (Throwable e) {
            e.printStackTrace();
        }finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }

    }

    public static void phantomReference() throws InterruptedException {
        Object object = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object, referenceQueue);

        System.out.println(object);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("==============");
        object = null;
        System.gc();
        Thread.sleep(1000);

        System.out.println(object);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

    }

}
