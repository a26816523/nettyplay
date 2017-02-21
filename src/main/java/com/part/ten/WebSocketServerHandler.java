package com.part.ten;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.UnknownFormatConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/2/15.
 */
public class WebSocketServerHandler  extends SimpleChannelInboundHandler<Object>{

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());

    private WebSocketServerHandshaker handshaker;

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx,(FullHttpRequest)msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
                    , HttpResponseStatus.BAD_REQUEST));
            return ;
        }

        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null ) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(),req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)frame.retain());
            return ;
        }

        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return ;
        }

        if (!(frame instanceof  TextWebSocketFrame)) {
            throw new UnknownFormatConversionException(
                    String.format("%s frame types not supported",frame.getClass().getName()));
        }

        String request = ((TextWebSocketFrame)frame).text();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received ",ctx.channel(),request));
        }
        ctx.channel().write(new TextWebSocketFrame(request + " ,欢迎使用Netty WebSocket服务，现在时刻:"
                + new java.util.Date().toString()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request
            , FullHttpResponse res){
        System.out.println(request.content().toString());
        if (res.status().code() != 200) {

            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH,res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(request) || res.status().code()!=200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private static boolean isKeepAlive(HttpMessage message) {
        String connection = message.headers().get(HttpHeaderNames.CONNECTION);
        return connection != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection)?false:(message.protocolVersion().isKeepAliveDefault()?!HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection):HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(connection));
    }
}
