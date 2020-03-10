package com.womenhz.swee.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import com.womenhz.swee.nio.NioDemoConfig;

public class EchoServerReactor implements Runnable {

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public EchoServerReactor() throws IOException {
        //初始选择器
        selector = Selector.open();
        //初始serversocket
        serverSocketChannel = ServerSocketChannel.open();

        //初始服务ip和端口
        InetSocketAddress address =
                new InetSocketAddress(NioDemoConfig.SERVER_SOCKET_IP,
                        NioDemoConfig.SERVER_SOCKET_PORT);
        //将socket绑定到地址上
        serverSocketChannel.socket().bind(address);

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        selectionKey.attach(new AcceptorHandler());
    }

    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                if (channel != null) {
                    new EchoHandler(selector, channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            }
        }catch (Exception e){

        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable handler = (Runnable) selectionKey.attachment();
        if (handler != null) {
            handler.run();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
