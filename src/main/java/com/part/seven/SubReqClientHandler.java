package com.part.seven;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zx on 2017/1/25.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter{

  public SubReqClientHandler() {
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0 ;i < 10; i++) {
      ctx.write(subReq(i));
    }
    ctx.flush();
  }

  private SubscribeReq subReq(int i) {
    SubscribeReq req = new SubscribeReq();
    req.setAddress("天津市北辰区旭日里10号楼5门611");
    req.setPhoneNumber("155xxxxxxx");
    req.setProductName("Netty 权威指南");
    req.setSubReqID(i);
    req.setUserName("zhaoxin");
    return req;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("Receive server response : ["+ msg +"]");
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
    ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
