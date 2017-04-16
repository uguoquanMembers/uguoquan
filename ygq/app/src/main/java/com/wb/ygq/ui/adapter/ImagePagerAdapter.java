package com.wb.ygq.ui.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wb.ygq.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private Activity mActivity;
    private List<String> pictureList;
    private boolean flag;

    public ImagePagerAdapter(Activity mActivity, List<String> pictureList) {
        this.mActivity = mActivity;
        this.pictureList = pictureList;
    }

    @Override
    public int getCount() {
        return pictureList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = LayoutInflater.from(mActivity).inflate(R.layout.item_image_pager,container,false);
        final ImageView iv_image = (ImageView) imageLayout.findViewById(R.id.iv_img);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.pb_loading);

//        iv_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flag){
//                    mActivity.isShowTitle(true);
//                    flag=false;
//                }else {
//                    mActivity.isShowTitle(false);
//                    flag=true;
//                }
//            }
//        });
//        iv_image.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
        String path = pictureList.get(position);
        Glide.with(mActivity)
                .load(path)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(iv_image){
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        spinner.setVisibility(View.GONE);

                    }
                });
        container.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
