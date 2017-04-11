package com.wb.ygq.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wb.ygq.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 版本更新处理模块
 */
public class ClientUpgrade {

    private Context context;

    private Thread downloadThread;

    private String downloadApkUrl = null;

    private String savePath = null;

    private String saveFilename = null;

    private boolean interceptFlag = false;

    private int progress = 0;

    private ProgressBar progressBar;

    private Dialog downloadDialog;

    private final static int DOWN_UPDATE = 1;

    private final static int DOWN_OVER = 2;

    private final static int DOWN_ERROR = 10;

    private ClientUpgradeCallback callback;

    public ClientUpgrade(Context context) {
        this.context = context;
    }

    public void downloadApk(String downloadUrl, ClientUpgradeCallback callback) {
        downloadApkUrl = downloadUrl;
        this.callback = callback;

        savePath = String.format("%s", FilePathUtil.getDownloadPath());
        saveFilename = String.format("%s%s", savePath, "ugq-upgrade.apk");
        showDownloadDialog();
        downloadThread = new Thread(mDownloadRunnable);
        downloadThread.start();
    }

    private Runnable mDownloadRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(downloadApkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30 * 1000);
                conn.connect();

                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                if (savePath.length() > 0) {
                    File file = new File(savePath);
                    if (!file.exists()) file.mkdirs();
                }

                String apkFilename = saveFilename;
                File apkFile = new File(apkFilename);
                FileOutputStream fos = new FileOutputStream(apkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;

                    progress = (int) (((float) count / length) * 100);
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                }
                while (!interceptFlag);

                fos.close();
                is.close();
            } catch (Exception e) {
                mHandler.sendEmptyMessage(DOWN_ERROR);
            }
        }

    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    progressBar.setProgress(progress);
                    tv_title.setText(String.format("正在下载：%d%%", progress));
                    break;
                case DOWN_OVER:
                    progress = 100;
                    progressBar.setProgress(progress);
                    tv_title.setText(String.format("正在下载：%d%%", progress));
                    // 添加判断，当前的activity的是否还存在
                    if (PublicUtil.isExistActivity(downloadDialog.getContext())) {
                        downloadDialog.dismiss();
                    }
                    installApk();
                    break;
                case DOWN_ERROR:
                    // 添加判断，当前的activity的是否还存在
                    if (PublicUtil.isExistActivity(downloadDialog.getContext())) {
                        downloadDialog.dismiss();
                    }
                    if (callback != null) callback.onFailed();
                    break;
                default:
                    break;
            }
        }
    };

    private TextView tv_title;

    private int installApk() {
        File file = new File(saveFilename);
        if (!file.exists()) return 1;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        downloadDialog.dismiss();
        if (callback != null) callback.onSuccess();

        return 0;
    }

    private void showDownloadDialog() {
        downloadDialog = new Dialog(context, R.style.NoTitleDialog);
        View ll = LayoutInflater.from(context).inflate(R.layout.client_upgrade, null);
        tv_title = (TextView) ll.findViewById(R.id.update_tv_title);
        tv_title.setText("正在下载");
        progressBar = (ProgressBar) ll.findViewById(R.id.progressBar1);
        downloadDialog.setContentView(ll);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setCancelable(false);
        downloadDialog.show();
    }

    public interface ClientUpgradeCallback {
        public void onCancel();

        public void onFailed();

        public void onSuccess();
    }
}
