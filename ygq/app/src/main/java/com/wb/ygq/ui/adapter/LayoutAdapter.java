/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wb.ygq.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.commit451.nativestackblur.NativeStackBlur;
import com.wb.ygq.R;
import com.wb.ygq.bean.HomeVideoBean;
import com.wb.ygq.ui.act.SZActivity;
import com.wb.ygq.ui.act.VideoActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.VipDialog;

import java.util.ArrayList;
import java.util.List;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 3;

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private final List<Integer> mItems;
    private List<HomeVideoBean.DataBean.IndexImgListBean> datas;
    private int mCurrentItemId = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        //        public final TextView title;
        private TextView tv_vp_title;
        private ImageView ima_vp_con;

        public SimpleViewHolder(View view) {
            super(view);
//            title = (TextView) view.findViewById(id.title);
            tv_vp_title = (TextView) view.findViewById(R.id.tv_vp_title);
            ima_vp_con = (ImageView) view.findViewById(R.id.ima_vp_con);
        }
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, DEFAULT_ITEM_COUNT);
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView, int itemCount) {
        mContext = context;
        datas = new ArrayList<>();
        mItems = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            addItem(i);
        }

        mRecyclerView = recyclerView;
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void updateDatas(List<HomeVideoBean.DataBean.IndexImgListBean> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_vp_home_down, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        if (datas.size() > 0) {
            final HomeVideoBean.DataBean.IndexImgListBean bean = datas.get(position);
//        final int itemId = mItems.get(position);
            holder.tv_vp_title.setText(bean.getTitle());
            if (PubConst.ZUANSHI.equals(SharedUtil.getString("vip", ""))) {
                Glide.with(mContext).load(bean.getImg()).crossFade().into(holder.ima_vp_con);
            } else {
                if ("0".equals(bean.getEmpty())) {
                    Glide.with(mContext).load(bean.getImg()).crossFade().into(holder.ima_vp_con);
                } else if ("1".equals(bean.getEmpty())) {
                    //当时1的时候图片变虚
                    Glide.with(mContext).load(bean.getImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap process = NativeStackBlur.process(resource, 30);
                            holder.ima_vp_con.setImageBitmap(process);
                        }
                    });
                }
            }
            holder.ima_vp_con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PubConst.ZUANSHI.equals(SharedUtil.getString("vip", ""))) {
                        if ("99".equals(bean.getUrl())) {
//                                skip(VideoActivity.class, false);
                            Intent intent = new Intent(mContext, VideoActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("index", bean.getUrl());
//                                skip(SZActivity.class, bundle, false);
                            Intent intent = new Intent(mContext, SZActivity.class);
                            intent.putExtra(PubConst.DATA, bundle);
                            mContext.startActivity(intent);
                        }
                    } else {
                        if ("1".equals(bean.getEmpty())) {
                            VipDialog.showVipDialog((Activity) mContext, false);
                        } else {
                            if ("99".equals(bean.getUrl())) {
                                Intent intent = new Intent(mContext, VideoActivity.class);
                                mContext.startActivity(intent);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("index", bean.getUrl());
//                                skip(SZActivity.class, bundle, false);
                                Intent intent = new Intent(mContext, SZActivity.class);
                                intent.putExtra(PubConst.DATA, bundle);
                                mContext.startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
