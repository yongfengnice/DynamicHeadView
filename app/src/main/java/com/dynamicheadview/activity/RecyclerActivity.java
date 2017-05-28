package com.dynamicheadview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.dynamicheadview.R;
import com.dynamicheadview.adapter.RecyclerItemAdapter;
import com.dynamicheadview.widget.DynamicHeadRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends Activity {
    private TextView mTvHead;
    private DynamicHeadRecyclerView mCvTest;

    private void assignViews() {
        mTvHead = (TextView) findViewById(R.id.tv_head);
        mCvTest = (DynamicHeadRecyclerView) findViewById(R.id.cv_test);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
    }

    private void initView() {
        assignViews();
        mCvTest.setDynamicView(mTvHead);
        mCvTest.setLayoutManager(new LinearLayoutManager(this));
        mCvTest.setHasFixedSize(true);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("测试数据：" + i);
        }
        mCvTest.setAdapter(new RecyclerItemAdapter(data));
    }
}
