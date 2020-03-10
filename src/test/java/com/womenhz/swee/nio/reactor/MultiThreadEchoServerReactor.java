package com.womenhz.swee.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import com.womenhz.swee.nio.NioDemoConfig;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MultiThreadEchoServerReactor {

    public static void main(String[] args) throws IOException {
        MultiThreadEchoServerReactor multiThreadEchoServerReactor = new MultiThreadEchoServerReactor();
        multiThreadEchoServerReactor.startService();
    }


    ServerSocketChannel serverSocketChannel;

    AtomicInteger next = new AtomicInteger(0);

    Selector[] selectors = new Selector[2];

    SubReactor[] subReactors = null;

    MultiThreadEchoServerReactor() throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress address = new InetSocketAddress(NioDemoConfig.SERVER_SOCKET_IP,
                NioDemoConfig.SERVER_SOCKET_PORT);

        serverSocketChannel.socket().bind(address);

        serverSocketChannel.configureBlocking(false);

        SelectionKey selectionKey = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);

        selectionKey.attach(new AcceptorHandler());

        SubReactor subReactor1 = new SubReactor(selectors[0]);

        SubReactor subReactor2 = new SubReactor(selectors[1]);

        subReactors = new SubReactor[]{subReactor1, subReactor2};
    }

    private void startService() {
        // 一子反应器对应一条线程
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try{
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null){
                    log.info(next.get());
                    new MultiThreadEchoHandler(selectors[next.get()], socketChannel);
                }
            }catch (Exception e) {

            }

            if (next.incrementAndGet() == selectors.length){
                next.set(0);
            }
        }
    }

    class SubReactor implements Runnable{

        final Selector selector;

        public SubReactor(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {

            try{
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();

                    Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();

                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        dispatch(selectionKey);
                    }
                    selectionKeySet.clear();

                }
            }catch (Exception e) {

            }
        }

        private void dispatch(SelectionKey selectionKey) {
            Runnable handler = (Runnable) selectionKey.attachment();
            if (handler != null) {
                handler.run();
            }
        }
    }

}




