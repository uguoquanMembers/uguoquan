package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;

/**
 * Description：充值
 * Created on 2017/4/4
 * Author :
 */
public class RechargeActivity extends BaseActivity {


    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recharge);
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_rechange);
        toolbar.setNavigationIcon(R.drawable.back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
