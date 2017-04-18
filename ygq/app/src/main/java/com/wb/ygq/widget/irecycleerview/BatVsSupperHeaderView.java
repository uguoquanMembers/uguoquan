package com.wb.ygq.widget.irecycleerview;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.ygq.R;


/**
 * Created by aspsine on 16/4/7.
 */
public class BatVsSupperHeaderView extends FrameLayout implements RefreshTrigger {

    private ImageView ivBatMan;

    private ImageView ivSuperMan;

    private ImageView ivVs;

    private ImageView iv_refreshing;

    private TextView tv_complete;

    private int mHeight;

    private Animator mRotationAnimator;

    public BatVsSupperHeaderView(Context context) {
        this(context, null);
    }

    public BatVsSupperHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatVsSupperHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_irecyclerview_bat_vs_supper_refresh_header_view, this);
        ivBatMan = (ImageView) findViewById(R.id.ivBatMan);
        ivSuperMan = (ImageView) findViewById(R.id.ivSuperMan);
        ivVs = (ImageView) findViewById(R.id.imageView);
        iv_refreshing = (ImageView) findViewById(R.id.iv_refreshing);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        mHeight = headerHeight;
        tv_complete.setVisibility(GONE);
    }

    @Override
    public void onMove(boolean finished, boolean automatic, int moved) {
        if (finished) {
            tv_complete.setVisibility(VISIBLE);
            ivVs.setVisibility(GONE);
        } else {
            ivVs.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        ivVs.setVisibility(GONE);
        iv_refreshing.setVisibility(VISIBLE);
        iv_refreshing.setBackgroundResource(R.drawable.loading);
        AnimationDrawable anim = (AnimationDrawable) iv_refreshing.getBackground();
        anim.start();
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        iv_refreshing.setVisibility(GONE);
        ivVs.setVisibility(GONE);
        tv_complete.setVisibility(VISIBLE);

    }

    @Override
    public void onReset() {
        tv_complete.setVisibility(GONE);
        ivVs.setVisibility(VISIBLE);
//        ivVs.setRotationY(0);
    }
}
