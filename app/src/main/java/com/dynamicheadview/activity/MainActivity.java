package com.dynamicheadview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dynamicheadview.R;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mListView;
    private Button mRecycleView;

    private void assignViews() {
        mListView = (Button) findViewById(R.id.listView);
        mRecycleView = (Button) findViewById(R.id.recycleView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();

        mListView.setOnClickListener(this);
        mRecycleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mListView) {
            Intent intent = new Intent(this, ListViewActivity.class);
            startActivity(intent);
        } else if (v == mRecycleView) {
            Intent intent = new Intent(this, RecyclerActivity.class);
            startActivity(intent);
        }
    }
}
