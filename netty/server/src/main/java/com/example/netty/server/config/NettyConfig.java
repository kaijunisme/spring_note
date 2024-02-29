package com.example.netty.server.config;

import com.example.netty.server.component.NettyServerBootstrap;
import com.example.netty.server.properties.NettyProperties;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

@Configuration
public class NettyConfig {

    @Bean
    public NioEventLoopGroup bossGroup(NettyProperties nettyProperties) {
        return new NioEventLoopGroup(nettyProperties.getBoss());
    }

    @Bean
    public NioEventLoopGroup workerGroup(NettyProperties nettyProperties) {
        return new NioEventLoopGroup(nettyProperties.getWorker());
    }

    @Bean
    public CommandLineRunner commandLineRunner(NettyServerBootstrap nettyServerBootstrap) {
        return args -> nettyServerBootstrap.start();
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> contextClosedListener(
        NettyServerBootstrap nettyServerBootstrap) {
        return event -> nettyServerBootstrap.shutdown();
    }
}
