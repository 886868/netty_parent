package com.mrle.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

public class HeartBeatsClient {
    private HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap bootstrap;

    private ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        ConnectionWatchdog watchdog = new ConnectionWatchdog(bootstrap, timer, port, host, true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        new StringDecoder(),
                        new StringEncoder(),
                        new HeartBeatClientHandler()
                };
            }
        };

        ChannelFuture future;

        try {
            synchronized (bootstrap) {
                bootstrap.handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });

                future = bootstrap.connect(host, port);
            }

            future.sync();
        } catch (Exception e) {
            throw new Exception("connects to fails");
        }
    }

    public static void main(String[] args) throws  Exception{
        int port = 9090;
        new HeartBeatsClient().connect(port, "127.0.0.1");
    }
}
