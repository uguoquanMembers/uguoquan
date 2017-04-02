package com.wb.ygq.ui.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.wb.ygq.R;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.ui.utils.MyUtil;

import java.util.ArrayList;

/**
 * Description：
 * Created on 2017/4/2
 * Author : 郭
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected BaseActivity baseContext = BaseActivity.this;

    /**
     * 持续多长时间不允许点击
     */
    private long keepTime = 10;

    /**
     * 上次点击的时间
     */
    private long preTime = 0;


    /**
     * 输入法管理器
     */
    public InputMethodManager imm;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DATE_CHANGED);
        filter.addAction(ACTION_TIME_CHANGED);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        showKeyboard(false);
        int action = ev.getAction();
        long downTime = 0;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:// 防止多个手指多个点击
                return true;
            case MotionEvent.ACTION_DOWN: // 按钮按下
                downTime = System.currentTimeMillis();
                if (downTime - preTime < keepTime) {
                    return true;
                }
                preTime = downTime;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void finish() {
        super.finish();
        rightOut(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MyUtil.showLog("BaseActivity.onTouchEvent.event ");
        showKeyboard(false);

        return super.onTouchEvent(event);
    }

    /**
     * 控制输入法是否显示
     *
     * @param isShow
     */
    protected void showKeyboard(boolean isShow) {
        if (imm == null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 做出右进的效果
     *
     * @param context
     */
    public static void leftOutRightIn(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 右侧出的效果
     *
     * @param context
     */
    public static void rightOut(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    /**
     * 封装Intent跳转
     *
     * @param clazz       要跳向的界面的class
     * @param isCloseSelf 是否关闭本界面
     */
    protected void skip(Class<?> clazz, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    protected void skip(Class<?> clazz, Bundle data, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    protected void skip(Class<?> clazz, ArrayList<?> dataList, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        if (dataList != null) intent.putExtra(PubConst.DATA, dataList);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    /**
     * 封装Intent跳转
     *
     * @param clazz 要跳向的界面的class
     */
    protected void skipForResult(Class<?> clazz, Bundle data, int requestCode) {
        Intent intent = new Intent(baseContext, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        startActivityForResult(intent, requestCode);
        leftOutRightIn(baseContext);
    }

    protected void skipForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(baseContext, clazz);
        startActivityForResult(intent, requestCode);
        leftOutRightIn(baseContext);
    }

    private static final String ACTION_DATE_CHANGED = Intent.ACTION_DATE_CHANGED;

    private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;

    public BroadcastReceiver timechanged = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            MyUtil.showLog("BaseActivity  时间修改 接收到广播  ");
            preTime = 0;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
