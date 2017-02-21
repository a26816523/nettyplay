package com.part.eleven.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * Created by dell on 2017/2/21.
 */
public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String response = packet.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith("谚语查询结果：")) {
            System.out.println(response);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
