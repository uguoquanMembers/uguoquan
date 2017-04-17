package com.wb.ygq.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.callback.OnClickCallBackListener;
import com.wb.ygq.ui.act.PayActivity;
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
    public static void showSex(final Activity context, String tvLeft, String tvRight, final OnClickCallBackListener onClickCallBackListener) {
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
                SharedUtil.setString(PubConst.KEY_SEX, key_sex[0]+"");
                onClickCallBackListener.onClickCallBack(null);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedUtil.setString(PubConst.KEY_SEX, key_sex[0]+"");
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

    private static String[] number = {"1", "2", "5", "18", "52", "88"};
    private static int[] res_back = {R.drawable.bg_dashang, R.drawable.bg_dashang_press};

    /**
     * @param context
     * @param title   标题
     */
    public static void showDaShangDia(final Activity context, final String id, String title) {
        final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
        //默认1元
        final String[] key_choose = {"1"};
        setAlpha(context, 50);
        final View[] view = {LayoutInflater.from(context).inflate(R.layout.layout_dialog_dashang, null)};
        TextView tv_dianame = (TextView) view[0].findViewById(R.id.tv_dianame);
        TextView text_pay_1 = (TextView) view[0].findViewById(R.id.text_pay_1);
        TextView text_pay_2 = (TextView) view[0].findViewById(R.id.text_pay_2);
        TextView text_pay_5 = (TextView) view[0].findViewById(R.id.text_pay_5);
        TextView text_pay_18 = (TextView) view[0].findViewById(R.id.text_pay_18);
        TextView text_pay_52 = (TextView) view[0].findViewById(R.id.text_pay_52);
        TextView text_pay_88 = (TextView) view[0].findViewById(R.id.text_pay_88);
        TextView tv_cancel = (TextView) view[0].findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) view[0].findViewById(R.id.tv_ok);
        final EditText et_money = (EditText) view[0].findViewById(R.id.et_money);
        textViews = new TextView[]{text_pay_1, text_pay_2, text_pay_5, text_pay_18, text_pay_52, text_pay_88};
        setTextColorSize(context, 0);//默认选中第一个
        tv_dianame.setText(title);
        for (int i = 0; i < textViews.length; i++) {
            final int finalI = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTextColorSize(context, finalI);
                    key_choose[0] = number[finalI];
                }
            });
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_text = et_money.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("money", TextUtils.isEmpty(et_text) ? key_choose[0] : et_text);
                bundle.putString("style", id);
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra(PubConst.DATA, bundle);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        showDialog(dialog, view[0], Gravity.CENTER, true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置文字大小颜色
     *
     * @param key
     */
    private static void setTextColorSize(Context context, int key) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == key) {//默认30
                textViews[i].setText(PublicUtil.formatTextView(context, number[i] + "", "RMB", R.style.textstyle_14_press, R.style.textstyle_14_press, 3));
            } else {
                textViews[i].setText(PublicUtil.formatTextView(context, number[i] + "", "RMB", R.style.textstyle_14_666666, R.style.textstyle_14_666666, 3));
            }
        }
        for (int i = 0; i < textViews.length; i++) {//设置点击背景颜色
            if (i == key) {
                textViews[i].setBackgroundResource(res_back[1]);
            } else {
                textViews[i].setBackgroundResource(res_back[0]);
            }
        }
    }

    /**
     * 打赏
     *
     * @param context
     * @param text
     */
    public static void showSingleText(final Activity context, final String text, final String id) {
        final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
        setAlpha(context, 50);
        final View view = LayoutInflater.from(context).inflate(R.layout.dia_ds, null);
        TextView tv_dia_money = (TextView) view.findViewById(R.id.tv_dia_money);
        TextView tv_btn_ok = (TextView) view.findViewById(R.id.tv_btn_ok);
        tv_dia_money.setText(text + "RMB");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setAlpha(context, 1);
            }
        });
        tv_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("money",text );
                bundle.putString("type", PubConst.DASHANG);
                bundle.putString("id",id);
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra(PubConst.DATA, bundle);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        showDialog(dialog, view, Gravity.CENTER, true);
    }
}
