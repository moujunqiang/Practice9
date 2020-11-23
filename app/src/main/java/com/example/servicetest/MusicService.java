package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MusicService extends Service {

    private String TAG = MusicService.class.getName();

    public MusicService() {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onCreate() {
        Log.i(TAG, "tempService启动");
        super.onCreate();

    }

    public void onDestroy() {
        Log.i(TAG, "MusicService");
        super.onDestroy();

    }

    //前台进程
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    public boolean onUnbind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return super.onUnbind(intent);
    }


}
