package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.ui.utils.MyUtil;
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
            MyUtil.showLog("pos++++++===" + ((SpPhotoViewHolder) holder).tv_itemspphoto_content + "=====" + position);
            ((SpPhotoViewHolder) holder).tv_itemspphoto_content.setText(friendListBean.getTitle());
            ((SpPhotoViewHolder) holder).tv_itemspphoto_name.setText(friendListBean.getName());
            Glide.with(mContext).load(friendListBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).crossFade().into(((SpPhotoViewHolder) holder).ima_itemspphoto_head);
            ((SpPhotoViewHolder) holder).rl_item_spphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((SpPhotoViewHolder) holder).rl_item_spphoto, friendListBean, position, 0);
                }
            });

        }
    }

    class SpPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_itemspphoto_head;
        private TextView tv_itemspphoto_name, tv_itemspphoto_content;
        private GridView grid_sp;
        private RelativeLayout rl_item_spphoto;

        public SpPhotoViewHolder(View itemView) {
            super(itemView);
            ima_itemspphoto_head = (ImageView) itemView.findViewById(R.id.ima_itemspphoto_head);
            tv_itemspphoto_name = (TextView) itemView.findViewById(R.id.tv_itemspphoto_name);
            tv_itemspphoto_content = (TextView) itemView.findViewById(R.id.tv_itemspphoto_content);
            rl_item_spphoto = (RelativeLayout) itemView.findViewById(R.id.rl_item_spphoto);
            grid_sp = (GridView) itemView.findViewById(R.id.grid_sp);
        }
    }
}
