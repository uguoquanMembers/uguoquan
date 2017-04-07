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
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.EmptyViewHolder;

/**
 * Description：
 * Created on 2017/4/6
 */
public class VideoPlayAdapter extends BaseRecyclerAdapter<CeshiBean> {
    private View headView;

    public VideoPlayAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new EmptyViewHolder(headView);
        } else if (viewType == 1) {
            return new VideoPlayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_playcomment, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoPlayViewHolder) {
            ((VideoPlayViewHolder) holder).tv_videoplay_name.setText(mItems.get(position).getName());
            ((VideoPlayViewHolder) holder).tv_videoplay_content.setText(mItems.get(position).getName());
            Glide.with(mContext).load(mItems.get(position).getIma()).bitmapTransform(new CropCircleTransformation(mContext)).crossFade().into(((VideoPlayViewHolder) holder).ima_videoplay_head);
        }
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }



    class VideoPlayViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_videoplay_head;
        private TextView tv_videoplay_name, tv_videoplay_content;

        public VideoPlayViewHolder(View itemView) {
            super(itemView);
            ima_videoplay_head = (ImageView) itemView.findViewById(R.id.ima_videoplay_head);
            tv_videoplay_name = (TextView) itemView.findViewById(R.id.tv_videoplay_name);
            tv_videoplay_content = (TextView) itemView.findViewById(R.id.tv_videoplay_content);
        }
    }
}
