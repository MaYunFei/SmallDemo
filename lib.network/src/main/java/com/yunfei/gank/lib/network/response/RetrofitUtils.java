package com.yunfei.gank.lib.network.response;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 */

public class RetrofitUtils {
  public static <V,T extends BaseResponse<V>> Func1<T,V> extractDataFunc(){
    return new Func1<T, V>() {
      @Override
      public V call(T t) {
        return t.getResults();
      }
    };
  }

  public static <T> Observable.Transformer<T, T> applySchedulers() {
    return transformer;
  }

  private static Observable.Transformer transformer = new Observable.Transformer() {
    @Override
    public Object call(Object observable) {
      return ((Observable) observable)
          //网络请求使用 io 线程
          .subscribeOn(Schedulers.io())
          //切换到主线程
          .observeOn(AndroidSchedulers.mainThread())
          .flatMap(new Func1() {
            @Override
            public Object call(Object response) {
              return flatResponse((BaseResponse) response);
            }
          });
    }
  };

  /**
   * 判断服务器返回数据是否正确
   * @param response
   * @param <T>
   * @return
   */
  private static <T extends BaseResponse> Observable<T> flatResponse(final T response) {
    return Observable.create(new Observable.OnSubscribe<T>() {
      @Override
      public void call(Subscriber<? super T> subscriber) {
        if (!subscriber.isUnsubscribed()) {
          //服务器判断
          if (!response.isError() && response.getResults() != null) {
            subscriber.onNext(response);
            subscriber.onCompleted();
          } else {
            //当服务器 异常 抛出异常
            subscriber.onError(new ServerException());
          }
        }
      }
    });
  }
}
