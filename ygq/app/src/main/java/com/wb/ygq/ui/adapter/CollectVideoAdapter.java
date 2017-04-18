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
        return new CollectVideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_classify_two, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CollectVideoViewHolder) {
            final CollcetVideoBean videoBean = mItems.get(position);
            ((CollectVideoViewHolder) holder).tv_name.setText(videoBean.getName());
            ((CollectVideoViewHolder) holder).tv_times.setVisibility(View.GONE);
            ((CollectVideoViewHolder) holder).tv_type.setVisibility(View.GONE);
            Glide.with(mContext).load(videoBean.getImg()).crossFade().into(((CollectVideoViewHolder) holder).ima_bg);
            Glide.with(mContext).load(videoBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).into(((CollectVideoViewHolder) holder).ima_head);
            ((CollectVideoViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((CollectVideoViewHolder) holder).rl_item, videoBean, position, 1);
                }
            });
            ((CollectVideoViewHolder) holder).rl_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.OnItemLongClick(((CollectVideoViewHolder) holder).rl_item, videoBean, position);
                    return false;
                }
            });
        }
    }

    class CollectVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_bg, ima_head;
        private TextView tv_name, tv_times, tv_type;
        private RelativeLayout rl_item;


        public CollectVideoViewHolder(View itemView) {
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
