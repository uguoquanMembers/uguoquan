package com.wb.ygq.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Locale;

public class PublicUtil {
    private static final String TAG = "PublicUtil";
    public static float density;


    /**
     * 设置控件所在的位置X，并且不改变宽高， X为绝对位置，此时Y可能归0
     */
    public static void setLayoutX(View view, int x) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x + 15, 15, x + margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 字符串格式化 String.format(,,)
     *
     * @param format
     * @param obj
     * @return
     */
    public static String formatString(String format, Object obj) {
        return String.format(Locale.getDefault(), format, obj);
    }

    /**
     * 判断activity是否存活
     *
     */
    @SuppressLint("NewApi")
    public static boolean isExistActivity(Context context) {
        if (context == null) {
            return false;
        }

        if (!(context instanceof Activity)) {
            return false;
        }
        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return false;
        }
        // 17以上才有 isDestroy方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 一个textview显示不同文字颜色大小
     *
     * @param context
     * @param str       前面文字
     * @param unit      后面文字
     * @param stylestr  前面文字大小颜色传入style
     * @param styleunit 后面文字大小颜色style
     * @param number    后面需要变化文字的数量
     * @return
     */
    public static SpannableString formatTextView(Context context, String str, String unit, int stylestr, int styleunit, int number) {
        String monthsContent = String.valueOf(str + unit);
        int lenMonths = monthsContent.length();
        SpannableString months = new SpannableString(monthsContent);
        months.setSpan(new TextAppearanceSpan(context, stylestr), 0, lenMonths - number, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        months.setSpan(new TextAppearanceSpan(context, styleunit), lenMonths - number, lenMonths, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return months;
    }

    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        density = dm.density;
        return (int) (dipValue * density + 0.5f);
    }

    /**
     * 设置viewpager伴随滚动
     *
     * @param linerlayout_recordlist
     * @param viewpager
     */
    public static void settingViewSlide(final LinearLayout linerlayout_recordlist, final ViewPager viewpager) {
        for (int i = 0; i < linerlayout_recordlist.getChildCount(); i++) {
            View childAt = linerlayout_recordlist.getChildAt(i);
            childAt.setTag(i);
            childAt.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    viewpager.setCurrentItem((Integer) (v.getTag()));
                }
            });
        }
        for (int i = 0; i < linerlayout_recordlist.getChildCount(); ++i) {
            linerlayout_recordlist.getChildAt(i).setEnabled(true);
        }
        linerlayout_recordlist.getChildAt(0).setEnabled(false);
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < linerlayout_recordlist.getChildCount(); i++) {
                    View at = linerlayout_recordlist.getChildAt(i);

                    at.setEnabled(true);
                    if (i == arg0) {
                        at.setEnabled(false);
                    }

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

        });
    }


    /**
     * 将以|分割的url 变成Arraylist
     *
     * @param ima_Url
     */
    public static ArrayList<String> formatImaToList(String ima_Url) {
        ArrayList<String> url_list = new ArrayList<String>();
        if (!TextUtils.isEmpty(ima_Url)) {
            String[] split = ima_Url.split("\\|");
            if (split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    url_list.add(split[i]);
                }
            }
        }
        return url_list;
    }


//    /**
//     * 设置图片大小
//     *
//     * @param context
//     * @param ima_addima
//     */
//    public static void setImaViewSize(Context context, ImageView ima_addima, int size) {
//        LayoutParams params = ima_addima.getLayoutParams();
//        int screenWidth = AppUtil.getScreenWidth(context);
//        params.width = screenWidth / size;
//        params.height = screenWidth / size;
//        ima_addima.setLayoutParams(params);
//    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isGPSOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    public static void setImaSize(Context context, ImageView ima, int c, int x) {
        int screenWidth = AppUtils.getScreenWidth(context);
        ViewGroup.LayoutParams layoutParams = ima.getLayoutParams();
        layoutParams.width = (screenWidth / c) * x;
        layoutParams.height = (screenWidth / c) * x;
        ima.setLayoutParams(layoutParams);
    }
}
