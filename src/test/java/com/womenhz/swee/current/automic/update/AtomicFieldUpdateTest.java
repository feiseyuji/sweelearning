package com.womenhz.swee.current.automic.update;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AtomicFieldUpdateTest {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(TestInteger.class, "id");
        TestInteger testInteger = new TestInteger();
        testInteger.id = new Integer(1);

        atomicIntegerFieldUpdater.addAndGet(testInteger, 2);

        log.info(testInteger.id);
    }

    static class TestInteger {
        //必须 volatile
        volatile int id;

    }

}
