package com.wb.ygq.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wb.ygq.R;

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

    public static void showVipDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_vip, null);
        TextView tv_ensure= (TextView) view.findViewById(R.id.tv_ensure);
        TextView tv_cancel= (TextView) view.findViewById(R.id.tv_cancel);
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(activity,)
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
}
