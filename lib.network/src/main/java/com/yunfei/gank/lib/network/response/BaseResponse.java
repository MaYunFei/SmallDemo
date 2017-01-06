package com.yunfei.gank.lib.network.response;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 * 请求返回类
 */

public class BaseResponse<T> {

  private boolean error;
  private T results;

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public T getResults() {
    return results;
  }

  public void setResults(T results) {
    this.results = results;
  }
}
