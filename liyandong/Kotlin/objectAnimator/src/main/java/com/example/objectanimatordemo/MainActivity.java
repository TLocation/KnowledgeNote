package com.example.objectanimatordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.objectanimatordemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(inflate.getRoot());
        inflate.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAnimatorAlpha();
            }
        });
        inflate.btnStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAnimatorRotation();
            }
        });
        inflate.btnStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAnimatorTranslationX();
            }
        });
        inflate.btnStart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAnimatorScaleY();
            }
        });
        inflate.btnStart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAnimatorFallingBall();
            }
        });
    }

    private void objAnimatorFallingBall() {
        ObjectAnimator alpha = ObjectAnimator.ofObject(inflate.tv5, "FallingPos",
                new FallingBallEvaluator(),
                new Point(0, 0), new Point(400, 400));
        alpha.setDuration(1000 * 2);
        alpha.start();
    }


    //透明度
    private void objAnimatorAlpha() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(inflate.tv, "alpha", 1, 0, 1);
        alpha.setDuration(2000);
        alpha.start();
    }

    //旋转
    private void objAnimatorRotation() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(inflate.tv2, "rotation", 0, 270, 0);
        alpha.setDuration(1000 * 8);
        alpha.start();
    }

    //x轴平移
    private void objAnimatorTranslationX() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(inflate.tv3, "translationX", 0, 200, -200, 0);
        alpha.setDuration(1000 * 4);
        alpha.start();
    }

    //y轴缩放
    private void objAnimatorScaleY() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(inflate.tv4, "scaleY", 0, 4, 1);
        alpha.setDuration(1000 * 4);
        alpha.start();
    }
}
