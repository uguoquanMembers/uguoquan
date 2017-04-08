/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.wb.ygq.widget.autoscrollviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.IBanner;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.widget.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter<T extends IBanner> extends RecyclingPagerAdapter {

    @Nullable
    private Context context;

    @Nullable
    private List<T> imageIdList;

    @Nullable
    private ArrayList<String> imgList;

    private int size;

    private boolean isInfiniteLoop;
    private int key_showstyle = 0;

    /**
     * 是Banner 还是url 默认false 代表Banner
     */
    private boolean isUrl = false;

    private LayoutInflater inflater;

    private onBannerItemClickListenter<T> mListener;

    public ImagePagerAdapter(@Nullable Context context, @Nullable ArrayList<String> imgList) {
        if (context == null) return;
        this.context = context;
        this.imgList = imgList;
        this.size = imgList == null ? 0 : imgList.size();
        isUrl = true;
        isInfiniteLoop = false;
        inflater = LayoutInflater.from(context);
    }

    public ImagePagerAdapter(int key_showstyle, @Nullable Context context, @Nullable List<T> imageIdList, onBannerItemClickListenter<T> listener) {
        if (context == null) return;
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = imageIdList == null ? 0 : imageIdList.size();
        isInfiniteLoop = false;
        inflater = LayoutInflater.from(context);
        mListener = listener;
       this.key_showstyle = key_showstyle;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Nullable
    @Override
    public View getView(final int position, @Nullable View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_single_img, container, false);
            holder.imageView = (ImageView) view.findViewById(R.id.single_img);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageView.setLayoutParams(params);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (!isUrl) {
            final T t = imageIdList.get(getPosition(position));
            if (key_showstyle !=  0) {//唯一的时候 圆角
                Glide.with(context).load(t.getBannerImg()).transform(new GlideRoundTransform(context, 15)).into(holder.imageView);
            } else {
                Glide.with(context).load(t.getBannerImg()).into(holder.imageView);
            }
            holder.imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(t);
                }
            });


        } else {
            String url = imgList.get(getPosition(position));
            MyUtil.showLog("22222222222222" + url);
//            Glide.with(context).load("http://shtml.asia-cloud.com/ZZSY/list_test2.png").into(holder.imageView);
//            ImageLoaderUtil.display(WAPI.urlFormatRemote(StringUtil.StrTrim(url)), holder.imageView, R.drawable.shop_detail_default_pic);
            if (!TextUtils.isEmpty(url)) {
                holder.imageView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        MyUtil.showLog("not url Banner ImageView onClick  点击查看大图");
//                        Intent intent = new Intent();
//                        intent.setClass(context, PhotoViewActivity.class);
//                        intent.putExtra(PubConst.DATA, imgList);
//                        intent.putExtra("current", getPosition(position));
//                        context.startActivity(intent);
                    }
                });
            }
        }

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    @NonNull
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    public interface onBannerItemClickListenter<T extends IBanner> {

        void onItemClick(T t);

    }
}
