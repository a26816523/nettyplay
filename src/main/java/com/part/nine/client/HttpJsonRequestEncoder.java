package com.part.nine.client;

import com.part.nine.AbstractHttpJsonEncoder;
import com.part.nine.HttpJsonRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonRequestEncoder extends AbstractHttpJsonEncoder<HttpJsonRequest> {
    protected void encode(ChannelHandlerContext ctx
            , HttpJsonRequest msg, List<Object> out) throws Exception {
        System.out.println("i am HttpJsonRequestEncoder");

        ByteBuf body = encode0(ctx,msg.getBody());
        FullHttpRequest request = msg.getRequest();
        if (request == null){
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.GET,"/do",body);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());
            headers.set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING,HttpHeaderValues.GZIP.toString() + ","
                    + HttpHeaderValues.DEFLATE.toString());
            headers.set(HttpHeaderNames.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE,"zh");
            headers.set(HttpHeaderNames.USER_AGENT,"Netty json Http client side");
            headers.set(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+json,application/json;q=0.9,*/*;q=0.8");

        }
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH,body.readableBytes());
        //HttpHeaders.setContentLength(request,body.readableBytes());
        out.add(request);
    }
}
