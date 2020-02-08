package com.womenhz.swee.current.automic;

import java.util.concurrent.atomic.AtomicReference;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AtomicReffercetest {


    public static void main(String[] args) {
        AtomicReference<RefferceObj> atomicReference = new AtomicReference<>();

        atomicReference.set(new RefferceObj(2L, "tom"));

        log.info(atomicReference.get());

        log.info(atomicReference.getAndSet(new RefferceObj(3L, "jerry")));

        log.info(atomicReference.get());
    }

    @Data
    static class RefferceObj {

        private long id;

        private String name;
        public RefferceObj(long id, String name) {

            this.id = id;

            this.name = name;
        }

    }
}
