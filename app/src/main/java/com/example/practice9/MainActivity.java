package com.example.practice9;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = "update";
    boolean tag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent=  new Intent(MainActivity.this,UpdateService.class);

        //开启更新服务
        Button onUpdate = findViewById(R.id.bt1);
        final Runnable tt = new Thread(thr);

        onUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                tag = true;
                startService(intent);
                new Thread(tt).start();

            }
        });

        //关闭更新服务
        Button offUpdate = findViewById(R.id.bt2);
        offUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = false;
                stopService(intent);

                Log.i(TAG, "停止更新服务");
            }
        });

        //开启音乐服务

        final Intent termpService = new Intent(MainActivity.this,tempService.class);
        final ServiceConnection sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                tempService ts = ((tempService.musicBinder)service).getService();
                if(ts!= null){
                    ts.playMusic();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };



        Button onMusic = findViewById(R.id.bt3);
        onMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //android8.0以上通过startForegroundService启动service
                    startForegroundService(termpService);
                } else {
                    startService(termpService);
                }
            }
        });

        Button offMusic = findViewById(R.id.bt4);
        offMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(termpService);
            }
        });


        final Button startIntent = findViewById(R.id.bt5);
        startIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,testService.class);

                startService(intent);
                long id  = Thread.currentThread().getId();
                Log.i(TAG, "主线程id:"+String.valueOf(id));

            }
        });


    }

    public void sendSMS (){
        // This is the code to find the running services
        ActivityManager am = (ActivityManager)this.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
        String message = null;

        for (int i=0; i<rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            Log.i("Service", "Process " + rsi.process + " with component " + rsi.service.getClassName());
            message =message+rsi.process ;
        }


    }


    //罗列进程
    private  boolean isServiceRunning(){
        ActivityManager activityManager =(ActivityManager) getSystemService(ACTIVITY_SERVICE);
        String str = "";
        for (ActivityManager.RunningServiceInfo ser :activityManager.getRunningServices(Integer.MAX_VALUE)){
//            if ("com.example.MyService".equals(ser.service.getClassName())){
//                str = str+ser.service.getClassName();
//                return  true;
//            }
            str = str+ser.service.getClassName();
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            return  true;
        }



        return  false;
    };


    Runnable thr = new Runnable() {
        @Override
        public void run() {
            while (tag){
                try {
                    sendMes();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };





    public void sendMes(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "default";
            String channelName = "默认通知";
            manager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("这里是标题")
                .setContentText("这是内容，这是内容，这是内容")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .build();
        manager.notify(1, notification);
    }




}
