package com.part.nine;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {

    final static String CHARSET_NAME = "utf-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
        String jsonStr = JSON.toJSONString(body);
        System.out.println("i am AbstractHttpJsonEncoder encode0："+jsonStr);
        ByteBuf encodeBuf = Unpooled.copiedBuffer(jsonStr,UTF_8);
        return encodeBuf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
}
