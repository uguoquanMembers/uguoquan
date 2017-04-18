package com.wb.ygq.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.callback.DialogChooseListener;
import com.wb.ygq.ui.act.RechargeActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaohe on 2017/4/14 0014.
 */

public class VipDialog {
//    <!--dialog样式-->
//    <style name="MyDialog" parent="android:style/Theme.Dialog">
//        <!--是否支持透window明度-->
//        <item name="android:windowIsTranslucent">true</item>
//        <!--背景颜色及透明程度-->
//        <item name="android:windowBackground">@android:color/transparent</item>
//        <!--是否有标题 -->
//        <item name="android:windowNoTitle">true</item>
//        <!--是否浮现在activity之上-->
//        <item name="android:windowIsFloating">true</item>
//        <!--是否模糊-->
//        <item name="android:backgroundDimEnabled">true</item>
//        <!--弹出动画-->
//        <item name="android:windowAnimationStyle">@android:style/Animation.Dialog</item>
//        <!--点击dialog外不消失-->
//        <item name="android:windowCloseOnTouchOutside">false</item>
//        <!--<item name="android:windowBackground">@android:color/transparent</item>-->
//        <item name="android:colorBackgroundCacheHint">@null</item>
//        <!--<item name="android:windowIsTranslucent">true</item>-->
//        <!--<item name="android:windowNoTitle">true</item>-->
//        <!--<item name="android:windowCloseOnTouchOutside">false</item>-->
//    </style>

    public static void showVipDialog(final Activity activity, final boolean flag) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_vip, null);
        TextView tv_ensure = (TextView) view.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RechargeActivity.class);
                activity.startActivity(intent);
                if (flag) {
                    activity.finish();
                }
                dialog.dismiss();

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        //获得当前窗体
        Window window = dialog.getWindow();

        //重新设置
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER | Gravity.TOP);

        lp.y = 550; // 新位置Y坐标
        window.setAttributes(lp);
        dialog.show();
    }

    /**
     * 带输入行的dialog
     * @param activity
     * @param chooseListener
     * @param hint
     * @param inputData
     * @param remindMsg
     */
    public static void getInputDialog(final Activity activity, final DialogChooseListener chooseListener , String hint, String inputData , String remindMsg) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_school_name, null);
        final EditText et_school_name = (EditText) view.findViewById(R.id.et_school_name);

        //设置hint
        et_school_name.setHint(hint);
        //设置输入框内容
        et_school_name.setText(inputData);
        et_school_name.requestFocus();
        //设置提醒信息
        TextView tv_remind= (TextView) view.findViewById(R.id.tv_remind);
        LinearLayout ll_remind= (LinearLayout) view.findViewById(R.id.ll_remind);
        if (!TextUtils.isEmpty(remindMsg)) {
            ll_remind.setVisibility(View.VISIBLE);
            tv_remind.setText(remindMsg);
        }else {
            ll_remind.setVisibility(View.GONE);
        }
        //确定按钮
        TextView tv_ensure = (TextView) view.findViewById(R.id.tv_ensure);
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListener.getInput(et_school_name.getText().toString());
//                View view1 = activity.getWindow().peekDecorView();
//                if (view1 != null) {
//                    InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputmanger.hideSoftInputFromWindow(view1.getWindowToken(), 0);
//                }
                dialog.dismiss();
            }
        });
        //关闭按钮
        ImageView iv_close= (ImageView) view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                View view1 = activity.getWindow().peekDecorView();
//                if (view1 != null) {
//                    InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputmanger.hideSoftInputFromWindow(view1.getWindowToken(), 0);
//                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        //获得当前窗体
        Window window = dialog.getWindow();

        //重新设置
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER | Gravity.TOP);

        lp.y = 250; // 新位置Y坐标
        window.setAttributes(lp);
        dialog.show();
        final InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                imm.showSoftInput(et_school_name, 0);
            }
        }, 200);
    }

}
