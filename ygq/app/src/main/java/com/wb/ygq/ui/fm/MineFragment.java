package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.ui.act.AlreadyBuyActivity;
import com.wb.ygq.ui.act.RechargeActivity;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.PublicUtil;

/**
 * Description：
 * Created on 2017/4/2
 */
public class MineFragment extends BaseFragment {

    private View view;
    /**
     * 等级
     */
    private TextView tv_bt_rank;
    /**
     * 充值
     */
    private TextView tv_bt_recharge;
    /**
     * 已经购买的
     */
    private TextView tv_btmine_buy;
    /**
     * 收藏
     */
    private TextView tv_btmine_collect;
    private ImageView ima_recharge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_mine, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        tv_bt_rank = (TextView) view.findViewById(R.id.tv_bt_rank);
        tv_bt_recharge = (TextView) view.findViewById(R.id.tv_bt_recharge);
        tv_btmine_buy = (TextView) view.findViewById(R.id.tv_btmine_buy);
        tv_btmine_collect = (TextView) view.findViewById(R.id.tv_btmine_collect);
        ima_recharge = (ImageView) view.findViewById(R.id.ima_recharge);


    }

    @Override
    public void initData() {
        tv_bt_rank.setText(PublicUtil.formatTextView(mActivity, "1", "\n等级", R.style.textstyle_14_ff6633, R.style.textstyle_14_666666, 2));
        tv_bt_recharge.setText(PublicUtil.formatTextView(mActivity, "会员", "\n充值", R.style.textstyle_14_ff6633, R.style.textstyle_14_666666, 2));
        Glide.with(mActivity).load("http://7xwwfr.com1.z0.glb.clouddn.com/recharge_baoqi_bg.png").into(ima_recharge);
    }

    @Override
    public void setListener() {

        tv_bt_rank.setOnClickListener(this);
        tv_bt_recharge.setOnClickListener(this);
        tv_btmine_buy.setOnClickListener(this);
        tv_btmine_collect.setOnClickListener(this);
        ima_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_bt_rank://等级
                skip(RechargeActivity.class, false);
                break;
            case R.id.tv_bt_recharge://充值

                skip(RechargeActivity.class, false);
                break;
            case R.id.tv_btmine_buy://已购买的
                skip(AlreadyBuyActivity.class, false);
                break;
            case R.id.tv_btmine_collect://收藏

                break;
            case R.id.ima_recharge://图片点击

                break;

            default:
                break;
        }
    }
}
