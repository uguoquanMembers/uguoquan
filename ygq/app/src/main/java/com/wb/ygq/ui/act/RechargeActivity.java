package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;

/**
 * Description：充值
 * Created on 2017/4/4
 */
public class RechargeActivity extends BaseActivity {


    private Toolbar toolbar;

    private ImageView ima_zs, ima_zsopen, ima_bj, ima_bjopen, ima_sy, ima_syopen, ima_dy, ima_dyopen;
    private String[] arrayUrl = {"http://7xwwfr.com1.z0.glb.clouddn.com/img1.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img1a.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img2.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img2a.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img3.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img3a.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img4.png", "http://7xwwfr.com1.z0.glb.clouddn.com/img4a.png"};

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
        ima_zs = (ImageView) findViewById(R.id.ima_zs);
        ima_bj = (ImageView) findViewById(R.id.ima_bj);
        ima_sy = (ImageView) findViewById(R.id.ima_sy);
        ima_dy = (ImageView) findViewById(R.id.ima_dy);
        ima_zsopen = (ImageView) findViewById(R.id.ima_zsopen);
        ima_bjopen = (ImageView) findViewById(R.id.ima_bjopen);
        ima_syopen = (ImageView) findViewById(R.id.ima_syopen);
        ima_dyopen = (ImageView) findViewById(R.id.ima_dyopen);

    }

    @Override
    public void initData() {
        Glide.with(this).load(arrayUrl[0]).crossFade().into(ima_zs);
        Glide.with(this).load(arrayUrl[1]).crossFade().into(ima_zsopen);
        Glide.with(this).load(arrayUrl[2]).crossFade().into(ima_bj);
        Glide.with(this).load(arrayUrl[3]).crossFade().into(ima_bjopen);
        Glide.with(this).load(arrayUrl[4]).crossFade().into(ima_sy);
        Glide.with(this).load(arrayUrl[5]).crossFade().into(ima_syopen);
        Glide.with(this).load(arrayUrl[6]).crossFade().into(ima_dy);
        Glide.with(this).load(arrayUrl[7]).crossFade().into(ima_dyopen);

    }

    @Override
    public void setListener() {
        ima_zs.setOnClickListener(this);
        ima_bj.setOnClickListener(this);
        ima_sy.setOnClickListener(this);
        ima_dy.setOnClickListener(this);
        ima_zsopen.setOnClickListener(this);
        ima_bjopen.setOnClickListener(this);
        ima_syopen.setOnClickListener(this);
        ima_dyopen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ima_zs:
                MyUtil.showLog("点击的==" + ima_zsopen);
                ima_zsopen.setVisibility(ima_zsopen.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.ima_bj:
                ima_bjopen.setVisibility(ima_bjopen.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.ima_sy:
                ima_syopen.setVisibility(ima_syopen.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.ima_dy:
                ima_dyopen.setVisibility(ima_dyopen.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.ima_zsopen:
                ToastUtil.showToast("充值1");
                SharedUtil.setString(PubConst.VIP_KEY_ZS, "http://7xwwfr.com1.z0.glb.clouddn.com/img1.png");
                break;
            case R.id.ima_bjopen:
                ToastUtil.showToast("充值2");
                SharedUtil.setString(PubConst.VIP_KEY_BJ, "http://7xwwfr.com1.z0.glb.clouddn.com/img2.png");
                break;
            case R.id.ima_syopen:
                ToastUtil.showToast("充值3");
                SharedUtil.setString(PubConst.VIP_KEY_SY, "http://7xwwfr.com1.z0.glb.clouddn.com/img3.png");
                break;
            case R.id.ima_dyopen:
                ToastUtil.showToast("充值4");
                SharedUtil.setString(PubConst.VIP_KEY_DY, "http://7xwwfr.com1.z0.glb.clouddn.com/img4.png");
                break;

            default:
                break;
        }
    }
}
