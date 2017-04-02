package com.wb.ygq.ui.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Description：
 * Created on 2017/4/2
 * Author : 郭
 */
public  class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
//        CrashHandler.getInstance().init(this);

    }




    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    /**
     * 获得当前进程号
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context)
    {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses())
        {
            if (appProcess.pid == pid) { return appProcess.processName; }
        }
        return null;
    }

    public static MyApplication getInstance()
    {
        return instance;
    }

}
