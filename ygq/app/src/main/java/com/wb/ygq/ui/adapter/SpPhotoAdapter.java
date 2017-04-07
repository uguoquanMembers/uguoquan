package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.CropCircleTransformation;

/**
 * Description： 私拍朋友圈适配器
 * Created on 2017/4/3
 */
public class SpPhotoAdapter extends BaseRecyclerAdapter<FriendListBean> {
    public SpPhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpPhotoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_photo, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SpPhotoViewHolder) {
            final FriendListBean friendListBean = mItems.get(position);
            ((SpPhotoViewHolder) holder).tv_itemspphoto_content.setText(friendListBean.getTitle());
            ((SpPhotoViewHolder) holder).tv_itemspphoto_name.setText(friendListBean.getName());
            ((SpPhotoViewHolder) holder).tv_time.setText(friendListBean.getTime());
            ((SpPhotoViewHolder) holder).tv_praise_count.setText(friendListBean.getFabulous());

            Glide.with(mContext).load(friendListBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).crossFade().into(((SpPhotoViewHolder) holder).ima_itemspphoto_head);
            ((SpPhotoViewHolder) holder).ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((SpPhotoViewHolder) holder).ll_container, friendListBean, position, 0);
                }
            });

        }
    }

    class SpPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_itemspphoto_head;
        private TextView tv_itemspphoto_name, tv_itemspphoto_content,tv_time,tv_praise_count;
        private GridView grid_sp;
        private LinearLayout ll_container;

        public SpPhotoViewHolder(View itemView) {
            super(itemView);
            ima_itemspphoto_head = (ImageView) itemView.findViewById(R.id.ima_itemspphoto_head);
            tv_itemspphoto_name = (TextView) itemView.findViewById(R.id.tv_itemspphoto_name);
            tv_itemspphoto_content = (TextView) itemView.findViewById(R.id.tv_itemspphoto_content);
            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container);
            grid_sp = (GridView) itemView.findViewById(R.id.grid_sp);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_praise_count= (TextView) itemView.findViewById(R.id.tv_praise_count);
        }
    }
}
