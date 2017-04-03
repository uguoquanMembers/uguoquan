package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;

/**
 * Description：
 * Created on 2017/4/3
 */
public class SpVideoAdapter extends BaseRecyclerAdapter<CeshiBean> {
    public SpVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    class  SpVideoViewHolder extends  RecyclerView.ViewHolder{

        public SpVideoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
