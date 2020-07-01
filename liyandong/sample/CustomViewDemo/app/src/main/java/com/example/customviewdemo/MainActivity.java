package com.example.customviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Handler han = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //1
        MainRunnableWraper wraper = new MainRunnableWraper("成员");
        wraper.start();

        //2
        new MainRunnableWraper("匿名").start();

        //3
        MainRunnable runnable = new MainRunnable("内部类"){
            @Override
            public void run() {
                new Thread(){
                    @Override
                    public void run() {
                        int index = 0;
                        for(;;){
                            index ++;
                            Log.i(TAG+" id:"+getThreadId(),name + "run index="+index);
                        }
                    }
                }.start();

            }
        };

        runnable.start();
    }

}
