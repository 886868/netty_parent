package com.mrle.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ServerBoot {
    private EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    private EventLoopGroup workGroup = new NioEventLoopGroup(4);
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) throws InterruptedException {
        ServerBootstrap sb = new ServerBootstrap();

        sb.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerInit());

        ChannelFuture future = sb.bind(address).sync();
        channel = future.channel();
        return future;
    }

    public void destory() {
        if (channel != null) {
            channel.close();
        }
        NettyConfig.group.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
