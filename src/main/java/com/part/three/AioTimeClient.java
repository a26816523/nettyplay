package com.part.three;

/**
 * Created by zx on 2017/1/23.
 */
public class AioTimeClient {

  public static void main(String[] args){
    int port = 8080;
    try{
      port = Integer.valueOf(args[0]);
    }catch(NumberFormatException e){

    }

    new Thread(new AsyncTimeClientHandler("127.0.0.1",port),
            "AIO-AsyncTimeClientHandler-001").start();
  }
}
