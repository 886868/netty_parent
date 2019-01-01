package com.mrle.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast("decoder", new StringDecoder());
        pip.addLast("encoder", new StringEncoder());
        pip.addLast("handler", new SocketHandler());
    }
}
