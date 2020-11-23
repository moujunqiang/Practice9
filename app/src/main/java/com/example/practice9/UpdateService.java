package com.example.practice9;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.core.app.NotificationCompat;

public class UpdateService extends Service {
    String TAG = "update";
    boolean tag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        final Thread t = new Thread(thread);
        t.start();

    }
    public  void onDestroy(){
        tag = false;

    }

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }


    final Runnable thread = new Runnable() {
        @Override
        public void run() {
            while (tag){
                try {
                    Thread.sleep(3000);
                    Log.i(TAG,"启动更新服务");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

