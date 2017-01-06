package com.yunfei.gank.app.main;

import com.yunfei.gank.lib.network.response.BaseResponse;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yunfei on 2017/1/4.
 * email mayunfei6@gmail.com
 * gank api
 * http://gank.io/api/data/数据类型/请求个数/第几页
 * http://gank.io/api/data/Android/10/1
 */

public interface CategoryServer {
  @GET("data/{type}/{count}/{page}") Observable<BaseResponse<List<CategoryEntity>>> getCategoryList(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}
