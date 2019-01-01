package com.mrle.hello;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Iterator;

public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server channelRead");
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());

        ctx.write("Server write: " + msg);

        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.add(ctx.channel());
        Iterator<Channel> channels =  NettyConfig.group.iterator();
        while (channels.hasNext()) {
            System.out.println(channels.next().id());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
