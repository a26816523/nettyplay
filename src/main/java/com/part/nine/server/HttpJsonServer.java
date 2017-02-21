package com.part.nine.server;

import com.part.nine.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/2/13.
 */
public class HttpJsonServer {

    public void run (final  int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            sc.pipeline().addLast("http-aggregator",new HttpObjectAggregator(6536));
                            sc.pipeline().addLast("json-decoder", new HttpJsonRequestDecoder(Order.class,true));
                            sc.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            sc.pipeline().addLast("json-encoder",new HttpJsonResponseEncoder());
                            sc.pipeline().addLast("xmlServerHandler",new HttpJsonServerHandler());

                        }
                    });
            String localIP = InetAddress.getLocalHost().getHostAddress().toString();
            ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
            System.out.println("Http 订购服务器启动，网址是："+ localIP +":" + port );
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main (String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        new HttpJsonServer().run(port);
    }

}
