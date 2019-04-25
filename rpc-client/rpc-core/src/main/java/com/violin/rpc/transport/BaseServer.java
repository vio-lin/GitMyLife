package com.violin.rpc.transport;


/**
 *  传输层可以使用多种实现
 * @author lin
 * Date: 2019-04-13
 */
public interface BaseServer {

  void run();

  void close();
}
