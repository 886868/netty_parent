package com.mrle.hello;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

// @Configuration
public class NettyConfig {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final String HOST = "127.0.0.1";
    public static final String PORT = "9090";
}
