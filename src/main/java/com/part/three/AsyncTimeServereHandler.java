package com.part.three;

import com.part.three.AcceptCompletionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zx on 2017/1/10.
 */
public class AsyncTimeServereHandler implements Runnable {

  private int port;
  public CountDownLatch latch;
  AsynchronousServerSocketChannel asynchronousServerSocketChannel;

  public AsyncTimeServereHandler(int port){
    this.port = port;
    try{
      asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
      asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
      System.out.println("The time server is start in port : "+ port);
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {

    latch = new CountDownLatch(1);
    doAccept();
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public  void  doAccept() {
    asynchronousServerSocketChannel
            .accept(this,new AcceptCompletionHandler());
  }
}
