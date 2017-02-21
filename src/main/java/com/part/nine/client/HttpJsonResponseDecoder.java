package com.part.nine.client;

import com.part.nine.AbstractHttpJsonDecoder;
import com.part.nine.HttpJsonResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<DefaultFullHttpResponse> {

    public HttpJsonResponseDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrintLog) {
        super(clazz, isPrintLog);
    }

    protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
        System.out.println("i am  HttpJsonResponseDecoder");
        HttpJsonResponse resHttpJsonResponse = new HttpJsonResponse(msg,decode0(ctx,msg.content()));
        out.add(resHttpJsonResponse);
    }
}
