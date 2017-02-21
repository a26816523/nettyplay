package com.part.three;

/**
 * Created by zx on 2017/1/10.
 */
public class AioTimeServer {

  public static void main(String[] args){
    int port = 8080;
    if (args !=null && args.length > 0) {
      try{
        port = Integer.valueOf(args[0]);
      }catch (NumberFormatException e){
        e.printStackTrace();
      }
    }

    AsyncTimeServereHandler timeServer = new AsyncTimeServereHandler(port);
    new Thread(timeServer,"AIO-AsyncTimeServerHandler-001").start();
  }
}
