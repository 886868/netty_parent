package com.mrle.controller;

import com.mrle.netty.NettyConfig;
import io.netty.channel.group.ChannelGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommController {

    @RequestMapping(value = "/usr/channel")
    public String send2Channel() {

        ChannelGroup group = NettyConfig.group;
        group.forEach(channel -> {
            channel.writeAndFlush("from server");
        });

        return "aaaa";
    }

}
