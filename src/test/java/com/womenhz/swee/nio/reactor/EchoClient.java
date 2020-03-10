package com.womenhz.swee.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import com.womenhz.swee.nio.NioDemoConfig;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EchoClient{

    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress(NioDemoConfig.SERVER_SOCKET_IP,
                NioDemoConfig.SERVER_SOCKET_PORT);
        SocketChannel socketChannel = SocketChannel.open(address);

        socketChannel.configureBlocking(false);

        //设置自旋
        while (!socketChannel.finishConnect()) {

        }

        log.info("client start success");

        Processor processor = new Processor(socketChannel);
        new Thread(processor).start();

    }

    static class Processor implements Runnable{

        final Selector selector;

        final SocketChannel socketChannel;

        public Processor(SocketChannel socketChannel) throws IOException {
            selector = Selector.open();
            this.socketChannel = socketChannel;
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
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
                        if (selectionKey.isWritable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(NioDemoConfig.SEND_BUFFER_SIZE);
                            Scanner scanner = new Scanner(System.in);
                            log.info("请输入发送内容：");
                            if (scanner.hasNext()) {
                                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                String next = scanner.next();
                                byteBuffer.put((LocalDateTime.now() +" >> "+next).getBytes());
                                //将写模式切换到读模式
                                byteBuffer.flip();

                                socketChannel.write(byteBuffer);
                                byteBuffer.clear();
                            }
                        }

                        if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int length = 0;
                            while ((length = socketChannel.read(byteBuffer)) > 0) {
                                byteBuffer.flip();
                                log.info("server echo: "+new String(byteBuffer.array(),0, length));
                                byteBuffer.clear();
                            }
                        }
                    }
                    selectionKeySet.clear();
                }
            }catch (Exception e) {

            }
        }
    }


}
