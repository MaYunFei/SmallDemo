package com.yunfei.gank.lib.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 */

public class ApiClient {
  private ApiClient() {
  }

  /**
   * 单例禁止多个
   */
  private static Retrofit retrofit = null;

  public static Retrofit retrofit() {
    if (retrofit == null) {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      /**
       *设置缓存，代码略
       */

      /**
       *  公共参数，代码略
       */
      builder.addInterceptor(addQueryParameterInterceptor);

      /**
       * 设置头，代码略
       */

      /**
       * Log信息拦截器，代码略
       */
      if (BuildConfig.DEBUG) {
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        builder.addInterceptor(loggingInterceptor);
      }

      /**
       * 设置cookie，代码略
       */

      //设置超时
      builder.connectTimeout(15, TimeUnit.SECONDS);
      builder.readTimeout(20, TimeUnit.SECONDS);
      builder.writeTimeout(20, TimeUnit.SECONDS);
      //错误重连
      builder.retryOnConnectionFailure(true);


      //以上设置结束，才能build(),不然设置白搭
      OkHttpClient okHttpClient = builder.build();
      retrofit = new Retrofit.Builder()
          .baseUrl(Config.BASE_RUL)
          .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build();
    }
    return retrofit;

  }

  //公共参数
  public static Interceptor addQueryParameterInterceptor = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request originalRequest = chain.request();
      Request request;
      String method = originalRequest.method();
      Headers headers = originalRequest.headers();
      HttpUrl modifiedUrl = originalRequest.url().newBuilder()
          // Provide your custom parameter here
          //                    .addQueryParameter("key", Config.APP_KEY)
          .build();
      request = originalRequest.newBuilder().url(modifiedUrl).build();
      return chain.proceed(request);
    }
  };
}
