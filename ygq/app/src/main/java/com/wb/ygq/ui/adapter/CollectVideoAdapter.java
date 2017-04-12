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
import com.wb.ygq.bean.CollcetVideoBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;

/**
 * Description：
 * Created on 2017/4/12
 */
public class CollectVideoAdapter extends BaseRecyclerAdapter<CollcetVideoBean> {
    public CollectVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectVideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_video, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CollectVideoViewHolder) {
            final CollcetVideoBean videoBean = mItems.get(position);
            ((CollectVideoViewHolder) holder).tv_video_name.setText(videoBean.getName());
            ((CollectVideoViewHolder) holder).tv_video_num.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(videoBean.getImg()).crossFade().into(((CollectVideoViewHolder) holder).iv_video_img);
            Glide.with(mContext).load(videoBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).into(((CollectVideoViewHolder) holder).ima_video_head);
            ((CollectVideoViewHolder) holder).ll_item_spvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((CollectVideoViewHolder) holder).ll_item_spvideo, videoBean, position, 1);
                }
            });
            ((CollectVideoViewHolder) holder).ll_item_spvideo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.OnItemLongClick(((CollectVideoViewHolder) holder).ll_item_spvideo, videoBean, position);
                    return false;
                }
            });
        }
    }

    class CollectVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_video_head, iv_video_img;
        private TextView tv_video_name, tv_video_num;
        private LinearLayout ll_item_spvideo;

        public CollectVideoViewHolder(View itemView) {
            super(itemView);
            ima_video_head = (ImageView) itemView.findViewById(R.id.ima_video_head);
            iv_video_img = (ImageView) itemView.findViewById(R.id.iv_video_img);
            tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            tv_video_num = (TextView) itemView.findViewById(R.id.tv_video_num);
            ll_item_spvideo = (LinearLayout) itemView.findViewById(R.id.ll_item_spvideo);
        }
    }
}
