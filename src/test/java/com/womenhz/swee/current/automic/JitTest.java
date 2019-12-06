package com.womenhz.swee.current.automic;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JitTest {

    static boolean init = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!init){
                    log.info("");
                }

                /**
                 * while(!init){} <=> while(true){}
                 *
                 * */
            }
        }).start();

        Thread.sleep(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                init = true;
                log.info("set init true");
            }
        }).start();
    }

}
