package com.womenhz.swee.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MultiThreadEchoHandler implements Runnable {

    final SocketChannel socketChannel;

    final SelectionKey selectionKey;

    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    static final int RECEIVING = 0, SENDING = 1;

    int state = RECEIVING;

    static ExecutorService pool = Executors.newFixedThreadPool(4);

    public MultiThreadEchoHandler(Selector selector, SocketChannel channel) throws IOException {
        socketChannel = channel;
        channel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        pool.execute(new AsyncTask());
    }

    public synchronized void asyncRun() {
        try{
            if (state == SENDING) {
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECEIVING;
            }else if (state == RECEIVING){

                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0){
                    log.info(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }

        }catch (Exception e) {

        }
    }

    class AsyncTask implements Runnable {

        @Override
        public void run() {
            MultiThreadEchoHandler.this.asyncRun();
        }
    }
}
