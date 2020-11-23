package com.example.practice9;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class tempService extends Service {

    String TAG = "update";
    public final IBinder ib = new musicBinder();
    private MediaPlayer mp = null;

    public class musicBinder extends Binder {
        tempService getService() {
            return tempService.this;
        }
    }

    public tempService() {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onCreate() {
        Log.i(TAG, "tempService启动");
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = null;
        NotificationManager manager = (NotificationManager) getApplication().getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            NotificationChannel mChannel = new NotificationChannel("123", "driver", NotificationManager.IMPORTANCE_LOW);//CHANNEL_ONE_ID自定义

            mChannel.setDescription("description");

            mChannel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);

            manager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this, "123")
                    .setChannelId("123")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("going_home")
                    .setContentIntent(pi)
                    .build();
        } else {
            // 提升应用权限
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("going_home")
                    .setContentIntent(pi)
                    .build();
        }
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        startForeground(10000, notification);
        playMusic();
    }

    public void onDestroy() {
        Log.i(TAG, "tempService关闭");
        stopPlayMusic();
        stopForeground(true);
        super.onDestroy();

    }

    //前台进程
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return ib;
    }


    public boolean onUnbind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return super.onUnbind(intent);
    }

    public void playMusic() {
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.going_home);
            mp.setLooping(false);
        }
        if (!mp.isPlaying()) {
            mp.start();
            Log.i(TAG, "start");
        }
    }


    public void stopPlayMusic() {
        if (mp != null & mp.isPlaying()) {
            mp.stop();
            Log.i(TAG, "stop");
        }
    }
}
