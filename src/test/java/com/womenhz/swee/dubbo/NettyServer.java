package com.womenhz.swee.dubbo;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class NettyServer {

    List<Object> serverBeans = Lists.newArrayList();

    public void openServer(int port) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        EventLoopGroup main = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);
        bootstrap.group(main, work);
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast("http-decode", new HttpRequestDecoder());
                ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                ch.pipeline().addLast("http-encode", new HttpRequestEncoder());
                ch.pipeline().addLast("servlet", new HttpServletHandler());

            }
        });

        ChannelFuture future = bootstrap.bind(port).sync();
        System.out.println("服务已经开启: "+port);
        future.channel().closeFuture().sync();

    }

    private class HttpServletHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            URI uri = new URI(msg.uri());
            String serverName = uri.getPath().substring(1).split("/")[0];
            String methodName = uri.getPath().substring(1).split("/")[1];
            Object result = findServer(serverName, methodName).apply(msg.content());
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            response.content().writeBytes(result.toString().getBytes());
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public Function<ByteBuf, Object> findServer(String serverName, String methodName) {
        Object server = serverBeans.stream().filter(e->e.getClass().getName().equalsIgnoreCase(serverName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("not serever " +serverName));

        Method method = Arrays.stream(server.getClass().getMethods())
                .filter(a->a.getName().equalsIgnoreCase(methodName))
                .findFirst()
                .orElseThrow(() ->new IllegalArgumentException("not method "+methodName));

        return args -> {
            try{
                return method.invoke(server, deserializationArgs(method, args));
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Object[] deserializationArgs(Method method, ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        Map<String, Object> map = JSON.parseObject(new String(bytes));
        String[] names = Arrays.stream(method.getParameters())
                .map(e -> e.getName())
                .collect(Collectors.toList())
                .toArray(new String[0]);
        Class<?>[] types = method.getParameterTypes();
        Object[] args = new Object[names.length];
        for (int i = 0; i < names.length; i++) {
            args[i] = map.get(names[i]);
        }
        return args;
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().openServer(8080);
    }

}
