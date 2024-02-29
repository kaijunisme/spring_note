package com.example.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class SocketChannelHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Message [server -> client]: " + msg);
        ctx.writeAndFlush("pong\r\n");
    }
}
