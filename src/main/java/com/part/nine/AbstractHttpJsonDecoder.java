package com.part.nine;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/2/13.
 */
public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T> {

    final static String CHARSET_NAME = "utf-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    private boolean isPrint;
    private Class<?> clazz;

    protected AbstractHttpJsonDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    protected  AbstractHttpJsonDecoder(Class<?> clazz,boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected  Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        String content = body.toString(UTF_8);
        System.out.println("i am AbstractHttpJsonDecoder");
        System.out.println("i am AbstractHttpJsonDecoder decode0:" + content);
        if (isPrint) {
            System.out.println("The body is : " + content);
        }
        Object result = JSON.parseObject(content,Order.class);
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
}
