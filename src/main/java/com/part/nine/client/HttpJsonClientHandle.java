package com.part.nine.client;

import com.part.nine.HttpJsonRequest;
import com.part.nine.HttpJsonResponse;
import com.part.nine.OrderFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonClientHandle extends
        SimpleChannelInboundHandler<HttpJsonResponse>{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpJsonRequest request = new HttpJsonRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpJsonResponse httpJsonResponse) throws Exception {
        System.out.println("The client receive response of http header is : "
                + httpJsonResponse.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : "
                + httpJsonResponse.getResult());
    }


}
