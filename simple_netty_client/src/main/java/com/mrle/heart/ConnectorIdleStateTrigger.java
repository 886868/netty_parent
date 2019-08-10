package com.mrle.heart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
    private static final ByteBuf HEARTBEAT_SEQ =
            Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HeartBeat", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent)evt).state();

            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HEARTBEAT_SEQ.duplicate());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
}
