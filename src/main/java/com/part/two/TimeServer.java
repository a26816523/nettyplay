package com.part.two;

/**nio time server.
 * Created by zx on 2017/1/5.
 */
public class TimeServer {
  /**主启动器.
   *
   * @param args 参数
   */
  public static void main(final  String[] args) {
    int port = 9090;
    MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

    new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
  }

}
