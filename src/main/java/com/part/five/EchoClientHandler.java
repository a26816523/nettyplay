package com.part.five;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zx on 2017/1/25.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

  private int counter;

  static final String ECHO_REQ = "Hi, HanMeiMei. Welcome to Netty.$_";

  public EchoClientHandler () {
    System.out.println("EchoClientHandler start");
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
//    ByteBuf message = null;
//    for (int i = 0 ; i < 100; i++) {
//      message = Unpooled.buffer(ECHO_REQ.getBytes().length);
//      message.writeBytes(ECHO_REQ.getBytes());
//      ctx.writeAndFlush(message);
//    }
    for (int i = 0 ; i< 100; i++) {
      ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
    System.out.println("This is "+ ++counter + " times receive server :[" + msg + "]");
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    System.out.println("read complete!");
    ctx.flush();
    ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

}
