package com.example.netty.server.component;

import com.example.netty.server.properties.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class NettyServerBootstrap {

    private final NettyProperties nettyProperties;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;
    private final SocketChannelPipeline socketChannelPipeline;

    public NettyServerBootstrap(NettyProperties nettyProperties, NioEventLoopGroup bossGroup,
        NioEventLoopGroup workerGroup, SocketChannelPipeline socketChannelPipeline) {
        this.nettyProperties = nettyProperties;
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
        this.socketChannelPipeline = socketChannelPipeline;
    }

    public void start() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyProperties.getTimeout())
            .childHandler(socketChannelPipeline);

        ChannelFuture future = serverBootstrap.bind(nettyProperties.getPort()).sync();
        System.out.println("啟動Netty 應用程式，監聽PORT號: " + nettyProperties.getPort());
        future.channel().closeFuture().sync();
    }

    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("關閉Netty 應用程式");
    }
}
