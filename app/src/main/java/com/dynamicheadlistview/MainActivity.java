package com.dynamicheadlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamicheadlistview.adapter.ItemAdapter;
import com.dynamicheadlistview.widget.DynamicHeadListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private TextView mTvHead;
    private DynamicHeadListView mLvTest;

    private void assignViews() {
        mTvHead = (TextView) findViewById(R.id.tv_head);
        mLvTest = (DynamicHeadListView) findViewById(R.id.lv_test);
    }


    private List<String> mStringList;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        assignViews();
        mLvTest.setDynamicView(mTvHead);
        mLvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), mStringList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mStringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mStringList.add("测试数据:---" + i);
        }

        mItemAdapter = new ItemAdapter(this, mStringList);
        mLvTest.setAdapter(mItemAdapter);
    }
}
