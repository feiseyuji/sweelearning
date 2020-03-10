package com.womenhz.swee.nio.javabase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ByteBufDemo {

    /**
     * 1. 属性
     *     int readerIndex;
     *     int writerIndex;
     *     private int markedReaderIndex;
     *     private int markedWriterIndex;
     *     private int maxCapacity;
     * 2. 3组方法
     *    容量
     *    写入
     *    读取
     *
     *   byteByf引用计数
     *   Pooled池化重新放到池子里
     *   未pooled heap结构（jvm回收）， direct(调用本地方法释放外部内存)
     *
     * */
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(5, 10);
        log.info("init = "+byteBuf);
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4,5});
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            log.info("read= "+byteBuf.getByte(i));
        }
        log.info("read= "+byteBuf);
        while (byteBuf.isReadable()) {
            log.info("cc= "+byteBuf.readByte());
        }
        log.info("init = "+byteBuf);

        //bytebuf引用计数
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer();
        log.info("create= "+byteBuf1.refCnt());
        byteBuf1.retain();
        log.info("ref= "+byteBuf1.refCnt());
        byteBuf1.release();
        log.info("rel1= "+byteBuf1.refCnt());
        byteBuf.release();
        log.info("rel2= "+byteBuf1.refCnt());
        byteBuf.release();
        log.info("rel3= "+byteBuf1.refCnt());
        byteBuf1.retain();
    }
}
