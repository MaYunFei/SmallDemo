package com.yunfei.gank.app.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static com.yunfei.gank.lib.utils.router.RoutrExtraKey.EXTRA_STRING_FROM;

public class SubDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_detail_activity);
        // app.detail/MaindetailActivity
//        Uri uri = Small.getUri(this);
//        if (uri != null) {
//            String from = uri.getQueryParameter("from");
//            Toast.makeText(this, from, Toast.LENGTH_SHORT).show();
//        }
        Intent intent = getIntent();
        if (intent != null){
            String from = getIntent().getStringExtra(EXTRA_STRING_FROM);
            Toast.makeText(this, from, Toast.LENGTH_SHORT).show();
        }

    }
}
