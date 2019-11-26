package com.womenhz.swee.current;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConcurrentTest {

    public static Executor executor = Executors.newFixedThreadPool(2);


    public static void main(String[] args) {
        int a = 0;
        for (int i = 0; i < 5; i++){
            executor.execute(()->{
                //log.info(++a);
            });
        }

    }


}
