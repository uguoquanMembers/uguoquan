package com.wb.ygq.ui.act;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.utils.AppUtils;

/**
 * Description：开屏图
 * Created on 2017/4/7
 */
public class SplashScreenActivity extends BaseActivity {

    private ImageButton imabtn_comein;

    Handler handler = new Handler();
    private Runnable run = new Runnable() {
        public void run() {
            skip(MainActivity.class, true);
        }
    };
    /**
     * 屏幕高度
     */
    private int screenHeight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_splash);
        initData();
        initView();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        imabtn_comein = (ImageButton) findViewById(R.id.imabtn_comein);
       ObjectAnimator.ofFloat(imabtn_comein, "translationY", 0F, -screenHeight / 2).setDuration(2000).start();
//        animator.setInterpolator(new OvershootInterpolator());
        handler.postDelayed(run, 3000);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imabtn_comein:
                handler.removeCallbacks(run);
                skip(MainActivity.class, true);
                break;

            default:
                break;
        }
    }

    @Override
    public void initData() {
        screenHeight = AppUtils.getScreenHeight(this);
    }

    @Override
    public void setListener() {
        imabtn_comein.setOnClickListener(this);
    }
}
