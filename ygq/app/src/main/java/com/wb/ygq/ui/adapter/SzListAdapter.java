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
import com.wb.ygq.bean.SZMessage;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.GlideRoundTransform;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SzListViewHolder) {
            SZMessage.DataBean dataBean = mItems.get(position);

            ((SzListViewHolder) holder).tv_item_name.setText(dataBean.getName());
//            Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), ((SzListViewHolder) holder).ima_sz_bg);
//            Palette
//            Palette palette = Palette.generate(bm);
//            if (palette.getLightVibrantColor() != null) {
//                name.setBackgroundColor(palette.getLightVibrantColor().getRgb());
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(palette.getLightVibrantColor().getRgb()));
//                // getSupportActionBar().
//
//            }
            Glide.with(mContext).load(dataBean.getImg()).transform(new GlideRoundTransform(mContext, 10)).into(((SzListViewHolder) holder).ima_sz_bg);
            //圆形头像
            Glide.with(mContext).load(dataBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).into(((SzListViewHolder) holder).ima_sz_head);
        }

    }

    /**
     *
     */
    class SzListViewHolder extends RecyclerView.ViewHolder {
        private ImageView  ima_sz_head;
        private ImageView ima_sz_bg;
        private TextView tv_item_name;

        public SzListViewHolder(View itemView) {
            super(itemView);
            ima_sz_bg = (ImageView) itemView.findViewById(R.id.ima_sz_bg);
            ima_sz_head = (ImageView) itemView.findViewById(R.id.ima_sz_head);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
