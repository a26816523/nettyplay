package com.part.nine.server;

import com.part.nine.AbstractHttpJsonEncoder;
import com.part.nine.HttpJsonResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonResponseEncoder extends AbstractHttpJsonEncoder<HttpJsonResponse> {
    protected void encode(ChannelHandlerContext ctx, HttpJsonResponse msg, List<Object> out) throws Exception {
        System.out.println("i am  HttpJsonResponseEncoder");
        ByteBuf body = encode0(ctx,msg.getResult());
        FullHttpResponse response = msg.getHttpResponse();
        if (response == null ) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,body);
        } else {
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion()
            ,msg.getHttpResponse().status(),body);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,body.readableBytes());
        out.add(response);
    }
}
