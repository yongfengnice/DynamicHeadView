package com.dynamicheadlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dynamicheadlistview.R;

import java.util.List;

/**
 * Created by yongfengnice on 5/26/2017.
 */

public class ItemAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mStringList;

    public ItemAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_test, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mTvItem.setText(mStringList.get(position));
        return convertView;
    }

    private class ViewHolder {
        private View mView;
        private TextView mTvItem;

        private void assignViews() {
            mTvItem = (TextView) mView.findViewById(R.id.tv_item);
        }

        public ViewHolder(View view) {
            mView = view;
            assignViews();
        }
    }
}
