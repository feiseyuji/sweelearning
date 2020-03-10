package com.womenhz.swee.nio.javabase;

import java.nio.ByteBuffer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BufferDemo {

    /**
     * 1.oio面向流 nio面向buffer
     * 2. oio blocked nio 非blocked
     * 3. oio no selector nio基于操作系统的选择器
     *
     * buffer:
     * 1. 3个属性：capacity， position(读写位置), limit（读写限制）
     * 2. api: allocate(n),put()
     * */
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());

        //0 1 2 3 4;
        for (int i = 0; i< 5; i++){
            byteBuffer.put(new Byte("0"+i));
        }
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
        byteBuffer.flip();//写模式->读模式
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
        for (int i = 0; i < 3 ; i++) {
            if (i == 2) {
                byteBuffer.mark();
            }
            int j = byteBuffer.get();
            log.info("j = "+j);
        }
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
        //byteBuffer.mark();
        //byteBuffer.rewind();
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
        byteBuffer.reset();
        for (int i = 2; i < 5; i++){
            int j = byteBuffer.get();
            log.info("j = "+j);
        }
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
        byteBuffer.clear();
        log.info("ca = "+byteBuffer.capacity()+ " po = "+byteBuffer.position()+" Lim= "+byteBuffer.limit());
    }
}
