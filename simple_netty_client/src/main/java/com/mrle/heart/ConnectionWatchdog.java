package com.mrle.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public abstract class ConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {    private Bootstrap bootstrap;
    private Timer timer;
    private  int port;

    private String host;

    private  volatile boolean reconnect = true;
    private int attempts;

    public ConnectionWatchdog(Bootstrap bootstrap, Timer timer, int port,String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        System.out.println("当前链路已激活，重试次数重置为0");
        attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // super.channelInactive(ctx);
        System.out.println("连接关闭");
        if (attempts < 12) {
            attempts++;
            int timeout = 2 << attempts;
            timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
        }

        ctx.fireChannelInactive();
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;

        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean succeed = future.isSuccess();

                if (!succeed) {
                    System.out.println("重连失败");
                    future.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("重连成功");
                }
            }
        });
    }
}