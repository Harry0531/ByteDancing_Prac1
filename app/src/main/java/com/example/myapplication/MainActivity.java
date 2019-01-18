package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static java.lang.System.exit;

public class MainActivity extends Activity {

    private ProgressBar bar ;//进度条
    private Button btn_Start;//开始按钮
    private TextView main_Content;//描述
    private TextView count ;//显示字数
    private ImageView img;//图片
    private int cnt;//点击次数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=findViewById(R.id.imageView);
        bar = findViewById(R.id.progressBarHorizontal);
        btn_Start = findViewById(R.id.btn_Start);
        main_Content = findViewById(R.id.main_Content);
        count = findViewById(R.id.text_Count);
        cnt =0;

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Start.setVisibility(View.GONE);
                Log.d("log","hide button_Start");

                main_Content.setVisibility(View.GONE);
                Log.d("log","hide main_Content");

                bar.setVisibility(View.VISIBLE);
                Log.d("log","appear bar");

                count.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                begin();
            }
        });


        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt++;
                count.setText(""+cnt);
            }
        });


    }
    private void  begin() {
        cnt=0;
        bar.setProgress(0);
        count.setText("0");
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bar.incrementProgressBy(1);
                if(bar.getProgress() == 100){
                    timer.cancel();
                    Log.e("TAG","时间到了");
                    handler.sendEmptyMessage(0x111);
                }
            }
        }, 100, 200);


    }


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x111:
                    end();
            }
        }
    };

    private  void end(){
        AlertDialog.Builder dialog;//提示框
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("时间到！");
        dialog.setMessage("一共点了"+cnt+"次，手速感人啊！");

        //左按钮
        dialog.setPositiveButton("再来一次",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: 用户选择：再来一次");
                        begin();

                    }
                });

        //右按钮
        dialog.setNegativeButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exit(0);
                    }
                }
        );
        dialog.create().show();
    }

}
