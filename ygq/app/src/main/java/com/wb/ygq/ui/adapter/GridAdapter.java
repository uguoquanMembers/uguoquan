package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.commit451.nativestackblur.NativeStackBlur;
import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseAdapter;
import com.wb.ygq.utils.AppUtils;

/**
 * Description：
 * Created on 2017/4/11
 */
public class GridAdapter extends BaseAdapter {
    private String isEmpty;

    public GridAdapter(Context context, String isEmpty) {
        super(context);
        this.isEmpty = isEmpty;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //勿动  后果自负
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_ima, null);
            holder = new ViewHolder();
            holder.ima = (ImageView) convertView.findViewById(R.id.ima);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str = (String) dataList.get(position);
        int screenWidth = AppUtils.getScreenWidth(context);
        ViewGroup.LayoutParams params = holder.ima.getLayoutParams();
        if (dataList.size() == 4) {
            params.height = screenWidth / 2;
            params.width = screenWidth / 2;
        } else if (dataList.size() < 4 && dataList.size() > 1) {
            params.height = screenWidth / dataList.size();
            params.width = screenWidth / dataList.size();
        } else if (dataList.size() > 4) {
            params.height = screenWidth / 3;
            params.width = screenWidth / 3;
        } else if (dataList.size() == 1)// 一个图的时候设置边距
        {
            params.height = (int) (screenWidth / 2);
            params.width = (int) (screenWidth / 2);// ( adapter图片不可设置pading
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.ima.getLayoutParams();
//            p.leftMargin = 30;
//            holder.ima.setLayoutParams(p);
        }
        holder.ima.setLayoutParams(params);
        if ("0".equals(isEmpty)) {
            Glide.with(context).load(str).crossFade().error(R.drawable.default_image).into(holder.ima);
        } else {
            //当时1的时候图片变虚
            final ViewHolder finalHolder = holder;
            Glide.with(context).load(str).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Bitmap process = NativeStackBlur.process(resource, 30);
                    finalHolder.ima.setImageBitmap(process);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ima;
    }
}
