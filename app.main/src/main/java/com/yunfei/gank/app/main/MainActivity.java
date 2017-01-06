package com.yunfei.gank.app.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yunfei.gank.lib.network.ApiClient;
import com.yunfei.gank.lib.network.response.BaseResponse;
import com.yunfei.gank.lib.network.response.RetrofitUtils;
import com.yunfei.gank.lib.network.response.ServerException;
import java.lang.reflect.Field;
import java.util.List;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

  private Toolbar mToolbar;
  private RecyclerView mRecyclerview;
  private MyAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setFieldValue(this,"mResources",null);
    setContentView(R.layout.activity_main);

    initView();

    ApiClient.retrofit()
        .create(CategoryServer.class)
        .getCategoryList("Android", 20, 1)
        //这里代码写的不好看先看着吧 也没有处理Rxjava 的生命后期问题 先这么着吧
        .compose(RetrofitUtils.<BaseResponse<List<CategoryEntity>>>applySchedulers())
        .map(RetrofitUtils.<List<CategoryEntity>, BaseResponse<List<CategoryEntity>>>extractDataFunc())
        .subscribe(new Action1<List<CategoryEntity>>() {
          @Override public void call(List<CategoryEntity> categoryEntities) {
            //处理数据结果
            mAdapter.setData(categoryEntities);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            if (throwable instanceof ServerException) {
              Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  private void initView() {
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle(getString(R.string.main));
    mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    mAdapter = new MyAdapter();
    setSupportActionBar(mToolbar);
    mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    mRecyclerview.setAdapter(mAdapter);
  }

  private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<CategoryEntity> data;

    public MyAdapter(List<CategoryEntity> data) {
      this.data = data;
    }

    public MyAdapter() {
    }

    public void setData(List<CategoryEntity> data) {
      this.data = data;
      notifyDataSetChanged();
    }

    public List<CategoryEntity> getData() {
      return data;
    }

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, parent, false);
      return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.tv.setText(data.get(position).getDesc());
    }

    @Override public int getItemCount() {
      if (data == null || data.isEmpty()) return 0;
      return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
      TextView tv;

      public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.tv_title);
      }
    }
  }

  public static Field getDeclaredField(Object object, String fieldName){
    Field field = null ;

    Class<?> clazz = object.getClass() ;

    for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
      try {
        field = clazz.getDeclaredField(fieldName) ;
        return field ;
      } catch (Exception e) {
        //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
        //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

      }
    }

    return null;
  }

  public static void setFieldValue(Object object, String fieldName, Object value){

    //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
    Field field = getDeclaredField(object, fieldName) ;

    //抑制Java对其的检查
    field.setAccessible(true) ;

    try {
      //将 object 中 field 所代表的值 设置为 value
      field.set(object, value) ;
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

  }

}
