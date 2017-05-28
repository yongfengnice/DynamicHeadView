package com.dynamicheadview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamicheadview.R;
import com.dynamicheadview.adapter.ListItemAdapter;
import com.dynamicheadview.widget.DynamicHeadListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity {
    private TextView mTvHead;
    private DynamicHeadListView mLvTest;

    private void assignViews() {
        mTvHead = (TextView) findViewById(R.id.tv_head);
        mLvTest = (DynamicHeadListView) findViewById(R.id.lv_test);
    }


    private List<String> mStringList;
    private ListItemAdapter mListItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
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

        mListItemAdapter = new ListItemAdapter(this, mStringList);
        mLvTest.setAdapter(mListItemAdapter);
    }
}
