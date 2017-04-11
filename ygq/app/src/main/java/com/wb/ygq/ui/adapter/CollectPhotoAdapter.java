package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.utils.PublicUtil;

/**
 * Description： 收藏照片 适配器
 * Created on 2017/4/11
 */
public class CollectPhotoAdapter extends BaseRecyclerAdapter<String> {
    public CollectPhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectPhotoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_image, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CollectPhotoViewHolder) {
            PublicUtil.setImaSize(mContext, ((CollectPhotoViewHolder) holder).ima, 2, 1);
            Glide.with(mContext).load(mItems.get(position)).crossFade().into(((CollectPhotoViewHolder) holder).ima);
            //长按回调
            ((CollectPhotoViewHolder) holder).ima.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.OnItemLongClick(((CollectPhotoViewHolder) holder).ima, mItems.get(position), position);
                    return false;
                }
            });
            ((CollectPhotoViewHolder) holder).ima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((CollectPhotoViewHolder) holder).ima, mItems.get(position), position, 1);
                }
            });
        }

    }

    class CollectPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima;

        public CollectPhotoViewHolder(View itemView) {
            super(itemView);
            ima = (ImageView) itemView.findViewById(R.id.ima_single);
        }
    }
}
