package com.mrle.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketHandler extends SimpleChannelInboundHandler<Object> {

    private AtomicInteger nConnection = new AtomicInteger();
//    WebSocketServerHandshaker

    /*public SocketHandler() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("connections: " + nConnection.get());
        }, 0, 2, TimeUnit.SECONDS);
    }*/
    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("in channel read");
        handMsg(ctx, msg);
    }

    // 连接server成功后分配的channl初始化方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        nConnection.incrementAndGet();
        NettyConfig.group.add(ctx.channel());
        System.out.println("channel counts:" + NettyConfig.group.size());
    }

    // 断开channel后触发的方法
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        nConnection.decrementAndGet();
        NettyConfig.group.remove(ctx.channel());
        ctx.channel().close();
    }

    // 完成本次请求的所有 read0 后触发该方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        System.out.println();
        ctx.writeAndFlush("read finish}");
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handMsg(ChannelHandlerContext ctx, Object msg) {
        System.out.print(msg);
    }
}
