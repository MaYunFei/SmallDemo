package com.yunfei.gank.lib.network.response;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 * 服务异常
 */

public class ServerException extends Exception {
  @Override public String getMessage() {
    return "服务器异常";
  }
}
