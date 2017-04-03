package com.wb.ygq.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.wb.ygq.ui.application.MyApplication;

/**
 * Toast提醒
 */
public class ToastUtil {
    public static void showToast(String str, int duration) {
        if (TextUtils.isEmpty(str)) return;
        Toast toast = Toast.makeText(MyApplication.getInstance(), str, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void showToast(String str) {
        showToast(str, Toast.LENGTH_SHORT);
    }
}
