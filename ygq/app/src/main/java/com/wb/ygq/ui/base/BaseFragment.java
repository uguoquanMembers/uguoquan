package com.wb.ygq.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wb.ygq.R;
import com.wb.ygq.ui.constant.PubConst;

import java.io.Serializable;

/**
 * Description：
 * Created on 2017/4/2
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private String name = this.getClass().getSimpleName();

    /**
     * 初始化标题
     */
    public abstract void initTitle();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听事件
     */
    public abstract void setListener();

    public Activity mActivity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.mActivity = activity;
    }



    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onClick(View v)
    {}





    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, Bundle data, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, Serializable serializableObj, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        if (serializableObj != null) intent.putExtra(PubConst.DATA, serializableObj);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 做出右进的效果
     *
     * @param context
     */
    public static void leftOutRightIn(Context context)
    {
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 右侧出的效果
     *
     * @param context
     */
    public static void rightOut(Context context)
    {
        ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
