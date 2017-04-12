package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.wb.ygq.ui.act.PersonalActivity;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.widget.CropCircleTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Description： 私拍朋友圈适配器
 * Created on 2017/4/3
 */
public class SpPhotoAdapter extends BaseRecyclerAdapter<FriendListBean> {
    /**
     * 存储数据
     */
    private List<String> urlList = new ArrayList<>();

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
            List<String> imgList = friendListBean.getImg();
            ////////////////////处理嵌套的gridview
            GridAdapter adapter = new GridAdapter(mContext);
            adapter.setData(urlList);
            ((SpPhotoViewHolder) holder).grid_sp.setClickable(false);
            ((SpPhotoViewHolder) holder).grid_sp.setPressed(false);
            ((SpPhotoViewHolder) holder).grid_sp.setEnabled(false);
            ((SpPhotoViewHolder) holder).grid_sp.setAdapter(adapter);
            if (imgList != null && !imgList.isEmpty()) {
                ((SpPhotoViewHolder) holder).grid_sp.setVisibility(View.VISIBLE);
                //保证最多只有9张图
                for (int i = 0; i < imgList.size(); i++) {
                    if (i < 9) {
                        urlList.addAll(imgList);
                    }
                }
                if (imgList.size() >= 5) {
                    ((SpPhotoViewHolder) holder).grid_sp.setNumColumns(3);
                } else if (imgList.size() == 4) {
                    ((SpPhotoViewHolder) holder).grid_sp.setNumColumns(2);
                } else {
                    ((SpPhotoViewHolder) holder).grid_sp.setNumColumns(imgList.size());
                }
                adapter.notifyDataSetChanged();
            } else {
                ((SpPhotoViewHolder) holder).grid_sp.setVisibility(View.GONE);
            }
            //点击头像跳转
            ((SpPhotoViewHolder) holder).ima_itemspphoto_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String string = SharedUtil.getString(PubConst.KEY_UID, "");
//                    if (TextUtils.equals(string, friendListBean.getId())) {//说明点击的是自己的头像 为了方式循环跳转  return
//                        return;
//                    } else {
                    //跳转个人朋友圈页面
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", friendListBean.getId());
                    intent.putExtra(PubConst.DATA, bundle);
                    intent.setClass(mContext, PersonalActivity.class);
                    mContext.startActivity(intent);
//                    }
                }
            });
        }
    }

    class SpPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_itemspphoto_head;
        private TextView tv_itemspphoto_name, tv_itemspphoto_content, tv_time, tv_praise_count;
        private GridView grid_sp;
        private LinearLayout ll_container;

        public SpPhotoViewHolder(View itemView) {
            super(itemView);
            ima_itemspphoto_head = (ImageView) itemView.findViewById(R.id.ima_itemspphoto_head);
            tv_itemspphoto_name = (TextView) itemView.findViewById(R.id.tv_itemspphoto_name);
            tv_itemspphoto_content = (TextView) itemView.findViewById(R.id.tv_itemspphoto_content);
            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container);
            grid_sp = (GridView) itemView.findViewById(R.id.grid_sp);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_praise_count = (TextView) itemView.findViewById(R.id.tv_praise_count);
        }
    }
}
