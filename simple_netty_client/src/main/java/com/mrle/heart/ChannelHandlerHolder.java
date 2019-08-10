package com.mrle.heart;

import io.netty.channel.ChannelHandler;

public interface ChannelHandlerHolder {
    ChannelHandler[] handlers();
}
