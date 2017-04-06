package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.widget.EmptyViewHolder;

/**
 * Description：
 * Created on 2017/4/5
 */
public class VideoAdapter extends BaseRecyclerAdapter<CeshiBean> {

    private View headView;

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new EmptyViewHolder(headView);
        } else if (viewType == 1) {
            return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sp_video,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VideoHolder) {
            final CeshiBean ceshiBean = mItems.get(position);
            ((VideoHolder) holder).tv_video_name.setText(ceshiBean.getName());
            ((VideoHolder) holder).tv_video_num.setText(position+10+"");
            ((VideoHolder) holder).ll_item_spvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(((VideoHolder) holder).ll_item_spvideo, ceshiBean, position, 1);
                }
            });

            Glide.with(mContext).load(ceshiBean.getIma()).crossFade().into(((VideoHolder) holder).ima_video_head);
        }
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView ima_video_head, ima_video_share;
        private TextView tv_video_name, tv_video_num;
        private LinearLayout ll_item_spvideo;
        public VideoHolder(View itemView) {
            super(itemView);
            ima_video_head = (ImageView) itemView.findViewById(R.id.ima_video_head);
            ima_video_share = (ImageView) itemView.findViewById(R.id.ima_video_share);
            tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            tv_video_num = (TextView) itemView.findViewById(R.id.tv_video_num);
            ll_item_spvideo = (LinearLayout) itemView.findViewById(R.id.ll_item_spvideo);
        }
    }
}
