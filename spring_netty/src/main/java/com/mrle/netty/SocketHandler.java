package com.mrle.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketHandler extends SimpleChannelInboundHandler<Object> {

//    WebSocketServerHandshaker
    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        handMsg(ctx, msg);
    }

    // 连接server成功后分配的channl初始化方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        NettyConfig.group.add(ctx.channel());
    }

    // 断开channel后触发的方法
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // super.channelInactive(ctx);
//        boardcastMsg(ctx, );
        NettyConfig.group.remove(ctx.channel());
        ctx.channel().close();
    }

    // 完成本次请求的所有 read0 后触发该方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handMsg(ChannelHandlerContext ctx, Object msg) {

    }
}
