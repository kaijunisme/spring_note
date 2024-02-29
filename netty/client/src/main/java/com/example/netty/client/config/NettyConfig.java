package com.example.netty.client.config;

import com.example.netty.client.component.NettyBootstrap;
import com.example.netty.client.properties.NettyProperties;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

@Configuration
public class NettyConfig {

    @Bean
    public NioEventLoopGroup nioEventLoopGroup(NettyProperties nettyProperties) {
        return new NioEventLoopGroup(nettyProperties.getWorker());
    }

    @Bean
    public CommandLineRunner commandLineRunner(NettyBootstrap nettyBootstrap) {
        return args -> nettyBootstrap.start();
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> contextClosedListener(
        NettyBootstrap nettyBootstrap) {
        return event -> nettyBootstrap.shutdown();
    }
}
