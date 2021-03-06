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
import com.wb.ygq.bean.SZMessage;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.RoundCornerImageView;

/**
 * Description：
 * Created on 2017/4/3
 * Author : 郭
 */
public class SzListAdapter extends BaseRecyclerAdapter<SZMessage.DataBean> {
    public SzListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SzListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sz, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SzListViewHolder) {
            final SZMessage.DataBean dataBean = mItems.get(position);

            ((SzListViewHolder) holder).tv_item_name.setText(dataBean.getName());

            Glide.with(mContext).load(dataBean.getImg()).crossFade().into(((SzListViewHolder) holder).ima_sz_bg);
            //圆形头像
            Glide.with(mContext).load(dataBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).into(((SzListViewHolder) holder).ima_sz_head);

            ((SzListViewHolder) holder).ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((SzListViewHolder) holder).ll_container, dataBean, position, 0);
                }
            });
        }

    }

    /**
     *
     */
    class SzListViewHolder extends RecyclerView.ViewHolder {
        private ImageView  ima_sz_head;
        private RoundCornerImageView ima_sz_bg;
        private TextView tv_item_name;
        private LinearLayout ll_container;

        public SzListViewHolder(View itemView) {
            super(itemView);
            ima_sz_bg = (RoundCornerImageView) itemView.findViewById(R.id.ima_sz_bg);
            ima_sz_head = (ImageView) itemView.findViewById(R.id.ima_sz_head);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            ll_container= (LinearLayout) itemView.findViewById(R.id.ll_container);
        }
    }
}
