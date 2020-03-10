package com.womenhz.swee.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EchoHandler implements Runnable {

    final SocketChannel channel;

    final SelectionKey selectionKey;

    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    static final int RECEIVING = 0, SENDING = 1;

    int state = RECEIVING;

    public EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        channel = socketChannel;
        socketChannel.configureBlocking(false);
        selectionKey = channel.register(selector, 0);
        selectionKey.attach(this);
        //注册read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }
    @Override
    public void run() {

        try {
            if (state == SENDING) {
                channel.write(byteBuffer);
                byteBuffer.clear();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECEIVING;
            }else if (state == RECEIVING){
                int length = 0;
                while ((length = channel.read(byteBuffer)) > 0){
                    log.info(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
        }catch (Exception e){
            log.info("e= "+e.getMessage());
        }

    }
}
