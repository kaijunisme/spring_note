package com.example.netty.client.handler;

import org.springframework.stereotype.Service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Service
public class SocketChannelHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Message [server -> client]: " + msg);
        ctx.writeAndFlush("pong\r\n");
    }
}
