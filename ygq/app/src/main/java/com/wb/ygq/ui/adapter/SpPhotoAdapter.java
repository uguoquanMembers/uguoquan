package com.wb.ygq.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.callback.OnCommentListener;
import com.wb.ygq.ui.act.BigPicActivity;
import com.wb.ygq.ui.act.PersonalActivity;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.DialogUtil;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.widget.CropCircleTransformation;
import com.wb.ygq.widget.MyGridView;

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
    private Activity mActivity;

    private OnCommentListener listener;
    private boolean isRealImg;

    public SpPhotoAdapter(Context context, Activity mActivity, OnCommentListener listener) {
        super(context);
        this.mActivity = mActivity;
        this.listener = listener;

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
            ((SpPhotoViewHolder) holder).tv_reward.setText("打赏总金额" + friendListBean.getReward() + "RMB");
            ((SpPhotoViewHolder) holder).tv_comment_count.setText(friendListBean.getComment());
            Glide.with(mContext).load(friendListBean.getHeadpic()).bitmapTransform(new CropCircleTransformation(mContext)).crossFade().into(((SpPhotoViewHolder) holder).ima_itemspphoto_head);
            ((SpPhotoViewHolder) holder).ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((SpPhotoViewHolder) holder).ll_container, mItems.get(position), position, 1);
                }
            });
            ((SpPhotoViewHolder) holder).ll_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.OnItemLongClick(((SpPhotoViewHolder) holder).ll_container, mItems.get(position), position);
                    return false;
                }
            });
            final ArrayList<String> urlList = new ArrayList<>();
            urlList.clear();
            List<String> imgList = friendListBean.getImg();
            ////////////////////处理嵌套的gridview
            GridAdapter adapter;
            if (SharedUtil.getBoolean(friendListBean.getId(), false)) {
                adapter = new GridAdapter(mContext, "0");
            } else {
                adapter = new GridAdapter(mContext, friendListBean.getEmpty());
            }
            adapter.setData(urlList);
            MyUtil.showLog("pos===3====" + mItems.get(3));

//            ((SpPhotoViewHolder) holder).grid_sp.setClickable(true);
//            ((SpPhotoViewHolder) holder).grid_sp.setPressed(false);
//            ((SpPhotoViewHolder) holder).grid_sp.setEnabled(false);
            ((SpPhotoViewHolder) holder).grid_sp.setAdapter(adapter);
            if (imgList != null && !imgList.isEmpty()) {
                ((SpPhotoViewHolder) holder).grid_sp.setVisibility(View.VISIBLE);
                //保证最多只有9张图
                urlList.clear();
                for (int i = 0; i < imgList.size(); i++) {
                    if (i < 9) {
                        urlList.add(imgList.get(i));
                    } else {
                        return;
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
            ((SpPhotoViewHolder) holder).grid_sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ("1".equals(friendListBean.getEmpty())) {
                        DialogUtil.showSingleText(mActivity, "9", friendListBean.getId());
                    } else {
                        Intent intent = new Intent(mActivity, BigPicActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        bundle.putStringArrayList("picPath", urlList);
                        intent.putExtra(PubConst.DATA, bundle);
                        mActivity.startActivity(intent);
                    }
                }
            });
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
                    bundle.putString("uid", friendListBean.getUserid());
                    intent.putExtra(PubConst.DATA, bundle);
                    intent.setClass(mContext, PersonalActivity.class);
                    mContext.startActivity(intent);
//                    }
                }
            });

            ((SpPhotoViewHolder) holder).ll_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.showDaShangDia(mActivity, friendListBean.getId(), "打赏给" + friendListBean.getName());
                }
            });

            //点赞
            final boolean[] isPrise = {false};
            ((SpPhotoViewHolder) holder).ll_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPrise[0]) {
                        Toast.makeText(mContext, "取消点赞", Toast.LENGTH_SHORT).show();
                        ((SpPhotoViewHolder) holder).iv_praise.setImageResource(R.drawable.img_praise);
                        isPrise[0] = false;
                        ((SpPhotoViewHolder) holder).tv_praise_count.setText(friendListBean.getFabulous());
                    } else {
                        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                        ((SpPhotoViewHolder) holder).iv_praise.setImageResource(R.drawable.img_praise_click);
                        isPrise[0] = true;
                        ((SpPhotoViewHolder) holder).tv_praise_count.setText((Integer.parseInt(friendListBean.getFabulous()) + 1) + "");
                    }
                }
            });
            ((SpPhotoViewHolder) holder).ll_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getComment(position);
                }
            });
        }
    }

    class SpPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_itemspphoto_head, iv_praise;
        private TextView tv_itemspphoto_name, tv_itemspphoto_content,
                tv_time, tv_praise_count, tv_reward, tv_comment_count;
        private MyGridView grid_sp;
        private LinearLayout ll_container, ll_dashang, ll_praise, ll_comment;

        public SpPhotoViewHolder(View itemView) {
            super(itemView);
            ima_itemspphoto_head = (ImageView) itemView.findViewById(R.id.ima_itemspphoto_head);
            tv_itemspphoto_name = (TextView) itemView.findViewById(R.id.tv_itemspphoto_name);
            tv_itemspphoto_content = (TextView) itemView.findViewById(R.id.tv_itemspphoto_content);
            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container);
            grid_sp = (MyGridView) itemView.findViewById(R.id.grid_sp);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_praise_count = (TextView) itemView.findViewById(R.id.tv_praise_count);
            ll_dashang = (LinearLayout) itemView.findViewById(R.id.ll_dashang);
            ll_praise = (LinearLayout) itemView.findViewById(R.id.ll_praise);
            tv_reward = (TextView) itemView.findViewById(R.id.tv_reward);
            iv_praise = (ImageView) itemView.findViewById(R.id.iv_praise);
            ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
            tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }


}
