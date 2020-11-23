package com.example.servicetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private Intent updateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    public void initView(){

        //开启更新服务
        Button btn1 = findViewById(R.id.bt1);
        btn1.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                updateService = new Intent(MainActivity.this, UpdateService.class);
                startService(updateService);

            }
        });

        //关闭更新服务
        Button btn2 = findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(updateService);
            }
        });

        //开启音乐服务

        final Intent musicService = new Intent(MainActivity.this, MusicService.class);


        Button bt3 = findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(musicService);

            }
        });

        Button bt4 = findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(musicService);
            }
        });


        final Button bt5 = findViewById(R.id.bt5);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntentDemoService.class);
                startService(intent);
                long id = Thread.currentThread().getId();
                Log.i(TAG, "主线程id:" + String.valueOf(id));

            }        });

    }


}
