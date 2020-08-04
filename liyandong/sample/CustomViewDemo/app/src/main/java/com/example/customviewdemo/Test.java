package com.example.customviewdemo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;


class MainRunnable {
    public  static final  String TAG = "Thread";
    private static int count = 0;

    public  static int getThreadId(){
        return count;
    }
    public    final String name;
    public MainRunnable(String name){
        this.name = name;
        count++;
    }
    public void run(){

    }
    public void start(){
        run();
    }
}
class  MainRunnableWraper extends MainRunnable{
    public MainRunnableWraper(String name) {
        super(name);
    }

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
}

public class Test extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    void test(){




    }
}
