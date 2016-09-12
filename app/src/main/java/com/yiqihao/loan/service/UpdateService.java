package com.yiqihao.loan.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.yiqihao.loan.R;
import com.yiqihao.loan.utils.T;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 武昌丶鱼 on 2016/5/11.
 * Description:app更新服务
 */
public class UpdateService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private Notification notification;
    private NotificationManager nm;
    private File tempFile = null;
    private int downloadPercent = 0;
    private RemoteViews contentView;
    private static final int DOWNLOAD_SUCCESS = 0;
    private static final int DOWNLOAD_CANCLE = 1;
    private static final int DOWNLOAD_FAILED = 2;
    private static final int DOWNLOAD_RUNNING = 3;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_RUNNING://下载中
                    downloadPercent = (int) msg.obj>100?100:(int)msg.obj;
                    contentView.setProgressBar(R.id.download_progressbar, 100, downloadPercent, false);
                    contentView.setTextViewText(R.id.download_percent,downloadPercent+"%");
                    notification.contentView = contentView;
                    nm.notify(0, notification);
                    break;
                case DOWNLOAD_SUCCESS://下载成功
                    downloadPercent = 0;
//                    nm.cancel(0);
                    contentView.setProgressBar(R.id.download_progressbar, 100, 100, false);
                    contentView.setTextViewText(R.id.download_percent,"下载完成,点击安装");
                    notification.contentView = contentView;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;

                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile((File) msg.obj), "application/vnd.android.package-archive");

                    PendingIntent contentIntent = PendingIntent.getActivity(UpdateService.this, 0, intent, 0);
                    notification.contentIntent = contentIntent;

                    nm.notify(0, notification);
                    T.showShort(UpdateService.this,"下载完成，点击安装");
                    installApk((File) msg.obj);
                    //结束更新的服务
                    stopSelf();
                    break;
                case DOWNLOAD_CANCLE://下载取消
                    stopSelf();
                    break;
                case DOWNLOAD_FAILED://下载失败
                    T.showShort(UpdateService.this,"下载失败，请稍后重试！");
                    stopSelf();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //notification的一些基本设置
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = android.R.drawable.stat_sys_download;
        notification.tickerText = getString(R.string.app_name) + "正在更新";
        notification.when = System.currentTimeMillis();
        notification.defaults = Notification.DEFAULT_LIGHTS;
//        notification.flags = Notification.FLAG_ONGOING_EVENT;;
        //状态栏中下载时显示的view
        contentView = new RemoteViews(getPackageName(), R.layout.download_notify);
        notification.contentView = contentView;

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        notification.contentIntent = contentIntent;
        nm.notify(0, notification);

        downLoadFile(intent.getStringExtra("url"));

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 开启新线程去下载apk
     *
     * @param path 下载的地址
     */
    private void downLoadFile(final String path) {
        new Thread() {
            @Override
            public void run() {

//                int progress = 0;
//                while(progress<100){
//                    try {
//                        Thread.sleep(1500);
//                        //随机生成1-10的进度
//                        int i = new Random().nextInt(10)+1;
//                        progress+=i;
//
//                        intent.putExtra("progress",progress);
//                        intent.setAction("com.reyzarc.mytimes");
//                        sendBroadcast(intent);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    Message msg = handler.obtainMessage(DOWNLOAD_RUNNING, progress);
//                    handler.sendMessage(msg);
//                }
//                Message msg = handler.obtainMessage(DOWNLOAD_SUCCESS,"ttt");
//                handler.sendMessage(msg);


                try {
                    URL url = null;
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    long length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    File file = new File(Environment.getExternalStorageDirectory(),
                            R.string.app_name + ".apk");
                    //判断文件是否存在，存在则先删除
                    if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                    }

                    //将输入流读取到缓冲区
                    BufferedInputStream bis = new BufferedInputStream(is);
                    //写入文件
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] bytes = new byte[1024];
                    int len;
                    int total = 0;
                    int percent = 0;
                    while ((len = bis.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                        total += len;
                        percent = (int) ((total * 100)/ length) ;
                        if (percent - downloadPercent >= 1) {//每1%更新进度
                            downloadPercent = percent;
                            Message msg = handler.obtainMessage(DOWNLOAD_RUNNING, downloadPercent);
                            handler.sendMessage(msg);
                        }
                    }

                    fos.close();
                    bis.close();
                    is.close();
                    Message msg = handler.obtainMessage(DOWNLOAD_SUCCESS, file);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = handler.obtainMessage(DOWNLOAD_FAILED);
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 安装apk
     *
     * @param file 要安装的apk文件
     */
    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
