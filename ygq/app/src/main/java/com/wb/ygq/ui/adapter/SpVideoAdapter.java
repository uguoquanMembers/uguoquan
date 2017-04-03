package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;

import org.w3c.dom.Text;

/**
 * Description：
 * Created on 2017/4/3
 */
public class SpVideoAdapter extends BaseRecyclerAdapter<CeshiBean> {
    public SpVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpVideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_photo, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpVideoViewHolder) {
            CeshiBean ceshiBean = mItems.get(position);
            ((SpVideoViewHolder) holder).tv_itemspphoto_content.setText(ceshiBean.getNum());
            ((SpVideoViewHolder) holder).tv_itemspphoto_name.setText(ceshiBean.getName());
            Glide.with(mContext).load(ceshiBean.getIma()).crossFade().into(((SpVideoViewHolder) holder).ima_itemspphoto_head);
        }
    }

    class SpVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima_itemspphoto_head;
        private TextView tv_itemspphoto_name, tv_itemspphoto_content;
        private GridView grid_sp;

        public SpVideoViewHolder(View itemView) {
            super(itemView);
            ima_itemspphoto_head = (ImageView) itemView.findViewById(R.id.ima_itemspphoto_head);
            tv_itemspphoto_name = (TextView) itemView.findViewById(R.id.tv_itemspphoto_name);
            tv_itemspphoto_content = (TextView) itemView.findViewById(R.id.tv_itemspphoto_content);
        }
    }
}
