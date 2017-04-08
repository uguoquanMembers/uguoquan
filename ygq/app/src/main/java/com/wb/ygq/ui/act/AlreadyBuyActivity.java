package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.ui.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/8
 * Author : 郭
 */
public class AlreadyBuyActivity extends BaseActivity {
    private Toolbar toolbar;
    private LinearLayout ll_addima;

    /**
     * 存储数据
     */
    private List<String> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_alreadybuy);
        initTitle();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_alreadybuy);
        toolbar.setNavigationIcon(R.drawable.back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        ll_addima = (LinearLayout) findViewById(R.id.ll_addima);
    }

    @Override
    public void initData() {
        String viptype_zs = SharedUtil.getString(PubConst.VIP_KEY_ZS, "");
        String viptype_bj = SharedUtil.getString(PubConst.VIP_KEY_BJ, "");
        String viptype_sy = SharedUtil.getString(PubConst.VIP_KEY_SY, "");
        String viptype_dy = SharedUtil.getString(PubConst.VIP_KEY_DY, "");
        if (!TextUtils.isEmpty(viptype_zs)) {
            dataList.add(viptype_zs);
        }
        if (!TextUtils.isEmpty(viptype_bj)) {
            dataList.add(viptype_bj);
        }
        if (!TextUtils.isEmpty(viptype_sy)) {
            dataList.add(viptype_sy);
        }
        if (!TextUtils.isEmpty(viptype_dy)) {
            dataList.add(viptype_dy);
        }
        showImaToUi();

    }

    /**
     * 展示图片
     */
    private void showImaToUi() {
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                ll_addima.addView(formatDataList(dataList.get(i)));
            }
        }
    }

    /**
     * 加载布局 显示图片
     *
     * @param s
     * @return
     */
    private View formatDataList(String s) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_single_ima, null);
        ImageView ima = (ImageView) inflate.findViewById(R.id.single_ima);
        Glide.with(this).load(s).crossFade().into(ima);
        return inflate;
    }

    @Override
    public void setListener() {

    }
}
