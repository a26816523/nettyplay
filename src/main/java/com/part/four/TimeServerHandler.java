package com.part.four;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
//5.x代码
//import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zx on 2017/1/24.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
 //5.x代码
//public class TimeServerHandler extends ChannelHandlerAdapter {
  private int counter;

  @Override
  public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {

    //拆包 粘包代码
    String body = (String) msg;
    //非拆包 粘包代码
//    ByteBuf buf = (ByteBuf) msg;
//    byte[] req = new byte[buf.readableBytes()];
//    buf.readBytes(req);
//    String body = new String(req, "UTF-8");
    System.out.println("The time server receive order :" + body + "; the counter is :" + ++counter);
    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis())
            .toString() : "BAD ORDER ";
    currentTime += System.getProperty("line.separator");
    ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
    ctx.write(resp);
  }

  @Override
  public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught (ChannelHandlerContext ctx ,Throwable cause) {
    ctx.close();
  }



}
