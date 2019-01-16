package com.han.demo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;


import com.han.base.net.ApiConstant;
import com.han.demo.entity.bean.UpdateVersion;
import com.han.framework.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

/**
 * 下载线程。 Handler 中更新 progress
 */
public class DownloadApkThread extends Thread {
    private Context context;
    private UpdateVersion updateVersion;
    private Handler handler;

    public DownloadApkThread(Context context, UpdateVersion updateVersion, Handler handler) {
        this.context = context;
        this.updateVersion = updateVersion;
        this.handler = handler;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        URL url = null;
        try {
            url = new URL(ApiConstant.BASE_SERVER_URL + "/" +  updateVersion.url);
        } catch (MalformedURLException ex) {

        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }

            String apkName = AppUtils.getAppName(context) + "-" + updateVersion.versionName + ".apk";
            String sdcardPath = Environment.getExternalStorageDirectory().getPath();
            String appPath = "/chuangyiyun/";

            File appDir = new File(sdcardPath + appPath);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }

            File apkFile = new File(appDir, apkName);

            int length = connection.getContentLength();
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(apkFile);

            byte data[] = new byte[4096];
            int readTotal = 0;
            int readCount = 0;
            while((readCount = inputStream.read(data)) != -1) {
                readTotal += readCount;
                outputStream.write(data, 0, readCount);
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = computePercent(readTotal, length);
                handler.sendMessage(msg);
            }

            Message completeMsg = handler.obtainMessage();
            completeMsg.what = 1;
            completeMsg.obj = apkFile.getAbsolutePath();
            handler.sendMessage(completeMsg);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ex) {

            }
        }

    }

    private int computePercent(int progress, int totalLength) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)progress/(float)totalLength);
        return (int) (Float.parseFloat(result) * 100);
    }
}
