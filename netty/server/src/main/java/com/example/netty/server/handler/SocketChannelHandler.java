package com.example.netty.server.handler;

import org.springframework.stereotype.Service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Service
public class SocketChannelHandler extends SimpleChannelInboundHandler<String> {

    private int readIdleCounter = 1;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Message [client -> server]: " + msg);
        readIdleCounter = 1;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();

            switch (state) {
                case READER_IDLE:
                    System.out.println((readIdleCounter * 10) + "s 沒收到客戶端訊息。IP: " + ctx.channel().remoteAddress());
                    if (readIdleCounter >= 3) {
                        System.out.println("已經 " + (readIdleCounter * 10) + "s 沒收到客戶端訊息，關閉客戶端連線。IP: " + ctx.channel().remoteAddress());
                        ctx.close();
                    } else {
                        readIdleCounter++;
                        ctx.writeAndFlush("ping\r\n");
                    }
                    break;
                case WRITER_IDLE:
                    System.out.println("寫空閒");
                    break;
                case ALL_IDLE:
                    System.out.println("全空閒");
                    break;
            }
        }

        super.userEventTriggered(ctx, evt);
    }
}
