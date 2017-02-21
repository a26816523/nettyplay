package com.part.seven;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * Created by zx on 2017/1/25.
 */
public class SubReqClient {

  public void connect (int port ,String host) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY,true)
              .handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers
                  .cacheDisabled(this.getClass().getClassLoader())));
                  socketChannel.pipeline().addLast(new ObjectEncoder());
                  socketChannel.pipeline().addLast(new SubReqClientHandler());
                }
              });
      ChannelFuture f = b.connect(host,port).sync();
      f.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
      group.shutdownGracefully();
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args != null && args.length > 0) {
      try {
        port = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
      }
    }
    new SubReqClient().connect(port,"127.0.0.1");
  }

}
