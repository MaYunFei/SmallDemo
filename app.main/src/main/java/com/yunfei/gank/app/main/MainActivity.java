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
import com.yunfei.gank.lib.utils.router.RouterUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setFieldValue(this,"mResources",null);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        ApiClient.retrofit()
                .create(CategoryServer.class)
                .getCategoryList("Android", 20, 1)
                //这里代码写的不好看先看着吧 也没有处理Rxjava 的生命后期问题 先这么着吧
                .compose(RetrofitUtils.<BaseResponse<List<CategoryEntity>>>applySchedulers())
                .map(RetrofitUtils.<List<CategoryEntity>, BaseResponse<List<CategoryEntity>>>extractDataFunc())
                .subscribe(new Action1<List<CategoryEntity>>() {
                    @Override
                    public void call(List<CategoryEntity> categoryEntities) {
                        //处理数据结果
                        mAdapter.setData(categoryEntities);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ServerException) {
                            Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        mToolbar.setTitle(getString(R.string.main));
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

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(data.get(position).getDesc());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Small.openUri("detail/subdetail?from=app.home", v.getContext());

//                    Intent intent = Small.getIntentOfUri("detail/subdetail", v.getContext());
//                    intent.putExtra("from","from app of intent");
//                    v.getContext().startActivity(intent);
                    RouterUtil.StartSubDetail(v.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
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



}
