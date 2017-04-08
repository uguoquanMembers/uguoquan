package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;


/**
 * Description：
 * Created on 2017/4/4
 */
public class SpVideoAdapter extends BaseRecyclerAdapter<CeshiBean> {

    public SpVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_video, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpVideoHolder) {
            CeshiBean ceshiBean = mItems.get(position);
            ((SpVideoHolder) holder).tv_video_name.setText(ceshiBean.getName());
            ((SpVideoHolder) holder).tv_video_num.setText("已经播放" + ceshiBean.getNum() + "万次");
            Glide.with(mContext).load(ceshiBean.getIma()).crossFade().into(((SpVideoHolder) holder).ima_video_head);
        }
    }

    class SpVideoHolder extends RecyclerView.ViewHolder {
        private ImageView ima_video_head;
        private TextView tv_video_name, tv_video_num;

        public SpVideoHolder(View itemView) {
            super(itemView);
            ima_video_head = (ImageView) itemView.findViewById(R.id.ima_video_head);

            tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            tv_video_num = (TextView) itemView.findViewById(R.id.tv_video_num);
        }
    }
}
