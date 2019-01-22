package com.mrle.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    public static void main(String[] args) {

        int port = 9090;
        String host = "127.0.0.1";

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
            }
        });

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            es.execute(()->{
                try {
                    ChannelFuture channelFuture = bootstrap.connect(host, port);
                    channelFuture.addListener((ChannelFutureListener) future -> {
                        if (!future.isSuccess()) {
                            System.out.println("连接失败, 退出!");
                            System.exit(0);
                        }
                    });
                    channelFuture.get();
                } catch (Exception e) {
                }
            });
        }



    }
}
