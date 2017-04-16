package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.GlideRoundTransform;

/**
 * Description：
 * Created on 2017/4/16
 */
public class ClassifyBjAdapter extends BaseRecyclerAdapter<VideoFMBean.DataBean.VideoListBean> {
    public ClassifyBjAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassifyBjHolder(LayoutInflater.from(mContext).inflate(R.layout.item_classify_two, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ClassifyBjHolder) {
            final VideoFMBean.DataBean.VideoListBean mVideoBean = mItems.get(position);
            ((ClassifyBjHolder) holder).tv_name.setText(mVideoBean.getName());
            ((ClassifyBjHolder) holder).tv_times.setText(mVideoBean.getCount());
            ((ClassifyBjHolder) holder).tv_type.setText(mVideoBean.getLabel());
            ((ClassifyBjHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((ClassifyBjHolder) holder).rl_item, mVideoBean, position, 1);
                }
            });
            Glide.with(mContext).load(mVideoBean.getHeadpic()).crossFade()
                    .bitmapTransform(new CropCircleTransformation(mContext)).into(((ClassifyBjHolder) holder).ima_head);
            Glide.with(mContext).load(mVideoBean.getImg()).crossFade()
                    .bitmapTransform(new GlideRoundTransform(mContext,10)).into(((ClassifyBjHolder) holder).ima_bg);
        }
    }


    class ClassifyBjHolder extends RecyclerView.ViewHolder {
        private ImageView ima_bg, ima_head;
        private TextView tv_name, tv_times, tv_type;
        private RelativeLayout rl_item;

        public ClassifyBjHolder(View itemView) {
            super(itemView);
            ima_head = (ImageView) itemView.findViewById(R.id.ima_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_times = (TextView) itemView.findViewById(R.id.tv_times);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ima_bg = (ImageView) itemView.findViewById(R.id.ima_bg);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
