package com.part.nine.server;

import com.part.nine.Address;
import com.part.nine.HttpJsonRequest;
import com.part.nine.HttpJsonResponse;
import com.part.nine.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonServerHandler extends SimpleChannelInboundHandler<HttpJsonRequest> {

    protected void channelRead0(final ChannelHandlerContext ctx, HttpJsonRequest jsonRequest) throws Exception {

        System.out.println("i am  HttpJsonServerHandlers");

        HttpRequest request = jsonRequest.getRequest();
        Order order = (Order)jsonRequest.getBody();
        System.out.println("Http server receive request : " + order );
        dobusiness(order);

        ChannelFuture future = ctx.writeAndFlush(new HttpJsonResponse(null,order));
        if(!isKeepAlive(request)){
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ctx.close();
                }
            });
        }
    }
    private void dobusiness(Order order){
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName("仁杰");
        List<String> midName = new ArrayList<String>();
        midName .add("李元芳");
        order.getCustomer().setMiddleNames(midName);
        Address address = order.getBillTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setState("河南道");
        address.setPostCode("123456");
        order.setBillTo(address);
        order.setShipTo(address);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer("失败：" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static boolean isKeepAlive(HttpMessage message) {
        String connection = message.headers().get(HttpHeaderNames.CONNECTION);
        return connection != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection)?false:(message.protocolVersion().isKeepAliveDefault()?!HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection):HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(connection));
    }
}
