package com.mrle;

import com.mrle.netty.NettyConfig;
import com.mrle.netty.ServerBoot;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ServerBoot serverBoot;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(NettyConfig.HOST, NettyConfig.PORT);

        ChannelFuture future = serverBoot.start(address);

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            serverBoot.destory();
        }));

        future.channel().closeFuture().syncUninterruptibly();
    }
}
