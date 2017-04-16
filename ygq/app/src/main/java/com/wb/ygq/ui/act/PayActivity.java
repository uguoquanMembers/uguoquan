package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;

/**
 * Description：充值
 * Created on 2017/4/15
 */
public class PayActivity extends BaseActivity {
    private Toolbar toolbar;
    private RelativeLayout rl_alpay, rl_wxpay;
    private ImageView ima_wxpay, ima_alpay;
    private Button button_pay;
    /**
     * 区分支付方式 1 支付宝 2微信  默认支付宝
     */
    private int pay_way = 1;
    /**
     * 支付的钱数
     */
    private String money;
    /**
     * 支付类型
     */
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay);
        initTitle();
        initView();
        initData();
        setListener();

    }

    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        Bundle bundle = getIntent().getBundleExtra(PubConst.DATA);
        money = bundle.getString("money");
        type = bundle.getString("type");

        rl_alpay = (RelativeLayout) findViewById(R.id.rl_alpay);
        rl_wxpay = (RelativeLayout) findViewById(R.id.rl_wxpay);
        ima_wxpay = (ImageView) findViewById(R.id.ima_wxpay);
        ima_alpay = (ImageView) findViewById(R.id.ima_alpay);
        button_pay = (Button) findViewById(R.id.button_pay);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        rl_alpay.setOnClickListener(this);
        rl_wxpay.setOnClickListener(this);
        button_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_alpay://支付宝
                ima_alpay.setBackgroundResource(R.drawable.check_box_true);
                ima_wxpay.setBackgroundResource(R.drawable.check_box_false);
                pay_way = 1;

                break;
            case R.id.rl_wxpay://微信
                ima_alpay.setBackgroundResource(R.drawable.check_box_false);
                ima_wxpay.setBackgroundResource(R.drawable.check_box_true);
                pay_way = 2;
                break;
            case R.id.button_pay://支付按钮
                gotoPay();
                break;

            default:
                break;
        }
    }

    /**
     * 支付按钮
     */
    private void gotoPay() {
        if (pay_way == 1) {
            ToastUtil.showToast("支付宝支付===");

        } else if (pay_way == 2) {
            ToastUtil.showToast("微信支付===");
        }
        if (!TextUtils.isEmpty(type)) {
            SharedUtil.setString("vip", type);
        }
    }
}
