package com.wb.ygq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.bean.AlreadyBuyBean;
import com.wb.ygq.ui.base.BaseRecyclerAdapter;
import com.wb.ygq.utils.MyUtil;

/**
 * Description： 已购买的  适配器
 * Created on 2017/4/14
 */
public class AlreadyBuyAdapter extends BaseRecyclerAdapter<AlreadyBuyBean> {
    public AlreadyBuyAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlreadyBuyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_alreadybuy, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AlreadyBuyViewHolder) {
            AlreadyBuyBean alreadyBuyBean = mItems.get(position);
            ((AlreadyBuyViewHolder) holder).tv_name.setText(alreadyBuyBean.getName());
            ((AlreadyBuyViewHolder) holder).tv_money.setText("支付金额：" + alreadyBuyBean.getTransaction_fee());
            ((AlreadyBuyViewHolder) holder).tv_time.setText("支付时间：" + alreadyBuyBean.getCreated_at());
            String type = alreadyBuyBean.getType();
            //1图片 2视频 3朋友圈
            if (TextUtils.equals(type, "1")) {
                ((AlreadyBuyViewHolder) holder).tv_type.setText("图片");
            } else if (TextUtils.equals(type, "2")) {
                ((AlreadyBuyViewHolder) holder).tv_type.setText("视频");
            } else if (TextUtils.equals(type, "3")) {
                ((AlreadyBuyViewHolder) holder).tv_type.setText("朋友圈");
            }
        }

    }

    class AlreadyBuyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_money, tv_time, tv_type;
        public AlreadyBuyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
