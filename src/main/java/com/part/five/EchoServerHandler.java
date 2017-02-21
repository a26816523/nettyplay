package com.part.five;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zx on 2017/1/25.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

  int counter = 0 ;

  @Override
  public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
    String body = (String) msg;
    System.out.println("This is " + ++counter + "time receive client :["
    + body + "]");
    body += "$_";
    ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
    ctx.writeAndFlush(echo);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
