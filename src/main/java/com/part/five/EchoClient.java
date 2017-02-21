package com.part.five;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by zx on 2017/1/25.
 */
public class EchoClient {

  public void connect (int port ,String host) throws Exception{
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY,true)
              .handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                  socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                  socketChannel.pipeline().addLast(new StringDecoder());
                  socketChannel.pipeline().addLast(new EchoClientHandler());
                }
              });
      ChannelFuture f = b.connect(host,port).sync();
      f.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args !=null && args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
      }catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    new EchoClient().connect(port,"127.0.0.1");
  }

}
