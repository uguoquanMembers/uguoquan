package com.wb.ygq.ui.utils;

import android.util.Log;

public class MyUtil {
    public static void showLog(Object log) {
        Log.e("尤果圈log======", String.valueOf(log));
    }

    public static void showLog(String tag, Object log) {
        Log.e(tag, String.valueOf(log));
    }

}
