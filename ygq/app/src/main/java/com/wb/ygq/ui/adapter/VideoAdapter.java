package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.EmptyViewHolder;

/**
 * Description：
 * Created on 2017/4/5
 */
public class VideoAdapter extends BaseRecyclerAdapter<VideoFMBean.DataBean.VideoListBean> {

    private View headView;

    public VideoAdapter(Context context) {
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
            return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_video,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VideoHolder) {
            final VideoFMBean.DataBean.VideoListBean mVideoBean = mItems.get(position);
            ((VideoHolder) holder).tv_video_name.setText(mVideoBean.getName());
            ((VideoHolder) holder).tv_video_num.setText(mVideoBean.getCount());
            ((VideoHolder) holder).tv_video_mark.setText(mVideoBean.getLabel());

            ((VideoHolder) holder).ll_item_spvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((VideoHolder) holder).ll_item_spvideo, mVideoBean, position, 1);
                }
            });
            Glide.with(mContext).load(mVideoBean.getImg()).crossFade().into(((VideoHolder) holder).iv_video_img);
            Glide.with(mContext).load(mVideoBean.getHeadpic()).crossFade()
                    .bitmapTransform(new CropCircleTransformation(mContext)).into(((VideoHolder) holder).ima_video_head);
        }
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView ima_video_head,iv_video_img;
        private TextView tv_video_name, tv_video_num,tv_video_mark;
        private LinearLayout ll_item_spvideo;
        public VideoHolder(View itemView) {
            super(itemView);
            ima_video_head = (ImageView) itemView.findViewById(R.id.ima_video_head);
            tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            tv_video_num = (TextView) itemView.findViewById(R.id.tv_video_num);
            ll_item_spvideo = (LinearLayout) itemView.findViewById(R.id.ll_item_spvideo);
            iv_video_img= (ImageView) itemView.findViewById(R.id.iv_video_img);
            tv_video_mark= (TextView) itemView.findViewById(R.id.tv_video_mark);
        }
    }
}
