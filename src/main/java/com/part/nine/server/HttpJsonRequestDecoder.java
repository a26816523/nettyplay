package com.part.nine.server;

import com.part.nine.AbstractHttpJsonDecoder;
import com.part.nine.HttpJsonRequest;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonRequestDecoder extends AbstractHttpJsonDecoder<FullHttpRequest> {

    public HttpJsonRequestDecoder(Class<?> clazz){
        this(clazz,false);
    }

    public HttpJsonRequestDecoder(Class<?> clazz,boolean isPrint){
        super(clazz,isPrint);
    }

    protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> out) throws Exception {
        System.out.println("i am HttpJsonRequestDecoder");
        if (!request.decoderResult().isSuccess()){
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return ;
        }

        HttpJsonRequest request1 = new HttpJsonRequest(request,decode0(ctx,request.content()));
        out.add(request1);
    }

    private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status
                ,Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n",CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
