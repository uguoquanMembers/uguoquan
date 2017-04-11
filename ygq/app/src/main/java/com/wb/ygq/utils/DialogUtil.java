package com.wb.ygq.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.constant.PubConst;

/**
 * 对话框工具类
 */
@SuppressLint("InflateParams")
public class DialogUtil {

    /**
     * 弹框时设置背景 阴影度 50%
     */
    private static final float WINDOW_ALPHA_DARK = 0.5f;
    private static TextView[] textViews;


    private static void setAlpha(Activity context, float alpha) {
        LayoutParams params = context.getWindow().getAttributes();
        params.alpha = alpha;

        context.getWindow().setAttributes(params);
    }

    /**
     * 显示dialog
     *
     * @param view
     */
    private static void showDialog(Dialog dialog, View view, int gravity, boolean cancelable) {
        Window window = dialog.getWindow();
        window.setGravity(gravity); // 此处可以设置dialog显示的位置
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
    }

    public static void showReminder(final Activity context, String title, String content, String tvLeft, String tvRight, final ConfirmDialog callBack) {
        final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
        setAlpha(context, 50);
        final View view = LayoutInflater.from(context).inflate(R.layout.dia_reminder, null);
        RelativeLayout rt_reminder = (RelativeLayout) view.findViewById(R.id.rt_reminder);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_title.setText(title);
        tv_content.setText(content);
        int screenWidth = AppUtils.getScreenWidth(context);
        ViewGroup.LayoutParams params = rt_reminder.getLayoutParams();
        MyUtil.showLog("屏幕的宽度为====" + screenWidth);
        params.width = (screenWidth / 3) * 2;
        params.height = screenWidth/2;
        view.setLayoutParams(params);
        showDialog(dialog, view, Gravity.CENTER, false);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onOKClick(null);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setAlpha(context, 1);
            }
        });
    }

    /**
     * 男女弹窗
     *
     * @param context
     * @param tvLeft
     * @param tvRight
     */
    public static void showSex(final Activity context, String tvLeft, String tvRight) {
        final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
        setAlpha(context, 50);
        //默认男 1 男  2女
        final int[] key_sex = {1};
        final View view = LayoutInflater.from(context).inflate(R.layout.dia_sex, null);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_sex);
        RelativeLayout rl_dia_sex = (RelativeLayout) view.findViewById(R.id.rl_dia_sex);
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == radioGroup.getChildAt(0).getId()) {
                    key_sex[0] = 1;
                    MyUtil.showLog("选择男");
                } else if (i == radioGroup.getChildAt(1).getId()) {
                    key_sex[0] = 2;
                    MyUtil.showLog("选择女");
                }
            }
        });
        int screenWidth = AppUtils.getScreenWidth(context);
        ViewGroup.LayoutParams params = rl_dia_sex.getLayoutParams();
        MyUtil.showLog("屏幕的宽度为====" + screenWidth);
        params.width = (screenWidth / 3) * 2;
        view.setLayoutParams(params);
        showDialog(dialog, view, Gravity.CENTER, false);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedUtil.setInt(PubConst.KEY_SEX, key_sex[0]);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedUtil.setInt(PubConst.KEY_SEX, key_sex[0]);
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setAlpha(context, 1);
            }
        });
    }


}
