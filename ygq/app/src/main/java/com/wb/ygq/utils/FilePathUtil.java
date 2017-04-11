package com.wb.ygq.utils;

import android.os.Environment;

import com.wb.ygq.ui.constant.PubConst;

import java.io.File;

/**
 * 各种路径获取类
 */
public class FilePathUtil {


    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 文件夹路径的根目录
     *
     * @return 如果sd卡存在就返回sd目录，如果不存在就返回内存包名路径
     */
    public static String getRootPath() {
        if (isSDCardExist()) {

            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        } else {
            return "/data/data/" + PubConst.PACKAGE_NAME;
        }
    }

    /**
     * 获取项目主文件夹路径，并创建该文件夹
     *
     * @return 主文件夹路径
     */
    public static String getMainRootPath() {
        String projectName = "finddevice";
        String mainRootPath = getRootPath() + File.separator + projectName + File.separator;
        File file = new File(mainRootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return mainRootPath;
    }


    /**
     * 获取项目主文件夹下下载文件存放的路径，并创建该文件夹
     *
     * @return
     */
    public static String getDownloadPath() {
        String downloadPath = getMainRootPath() + "download/";
        File file = new File(downloadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return downloadPath;
    }
}