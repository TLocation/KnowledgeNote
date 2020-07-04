package com.example.sweepgradientdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SweepGradientView extends View {
    private boolean isScanning = false;//是否扫描
    private float mRadius = 300f;
    private Paint paint;
    private float mDegrees = 0;
    private float mSpeed = 3;
    private int mSweepColor = Color.parseColor("#91D7F4");

    public SweepGradientView(Context context) {
        this(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSweep(canvas, getWidth() / 2, getHeight() / 2, mRadius);
        //扫描的旋转角度
        mDegrees = (mDegrees + (360 / mSpeed / 60));
        Log.d("SweepGradientView", "onDraw: " + mDegrees);
        invalidate();
    }

    private void drawSweep(Canvas canvas, int cx, int cy, float mRadius) {
        SweepGradient sweepGradient = new SweepGradient(cx, cy, Color.RED, Color.BLUE);
        paint.setShader(sweepGradient);
        canvas.rotate(-90 + mDegrees, cx, cy);
        canvas.drawCircle(cx, cy, mRadius, paint);
    }
}
