package com.part.three;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by zx on 2017/1/10.
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServereHandler> {

  @Override
  public void completed(AsynchronousSocketChannel result, AsyncTimeServereHandler attachment) {
    attachment.asynchronousServerSocketChannel.accept(attachment,this);
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    result.read(buffer,buffer,new ReadCompletionHandler(result));
  }

  @Override
  public void failed(Throwable exc, AsyncTimeServereHandler attachment) {
    exc.printStackTrace();
    attachment.latch.countDown();
  }
}
