package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {
    private String TAG = UpdateService.class.getName();
    boolean isStopService = true;

    @Override
    public void onCreate() {
        super.onCreate();
        final Thread thread = new Thread(runnable);
        thread.start();
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isStopService) {
                try {
                    Thread.sleep(10 * 1000);
                    Log.i(TAG, "有更新了。。。");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    public void onDestroy() {
        isStopService = false;

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }


}

