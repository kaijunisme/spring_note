package com.example.netty.client.component;

import com.example.netty.client.properties.NettyProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class NettyBootstrap {

    private final NettyProperties nettyProperties;
    private final NioEventLoopGroup workerGroup;
    private final SocketChannelPipeline socketChannelPipeline;

    public NettyBootstrap(NettyProperties nettyProperties, NioEventLoopGroup workerGroup,
        SocketChannelPipeline socketChannelPipeline) {
        this.nettyProperties = nettyProperties;
        this.workerGroup = workerGroup;
        this.socketChannelPipeline = socketChannelPipeline;
    }

    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap()
            .group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyProperties.getTimeout())
            .handler(socketChannelPipeline);

        ChannelFuture future = bootstrap.connect(nettyProperties.getHost(), nettyProperties.getPort()).sync();
        System.out.printf("啟動Netty Client 應用程式，連線Server IP:PORT: %s:%s\n", nettyProperties.getHost(), nettyProperties.getPort());
        future.channel().closeFuture().sync();
    }

    public void shutdown() {
        workerGroup.shutdownGracefully();
        System.out.println("關閉Netty Client 應用程式");
    }
}
