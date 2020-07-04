package com.example.customviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PieView extends View {
    private float mRadius = 300;
    private Paint paint;
    private float[] angles = {60, 100, 120, 80};
    private int[] colors = {Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
            Color.parseColor("#009688"), Color.parseColor("#FF8F00")};

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int starTangles = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == 1) {
                canvas.translate(
                        (float) Math.cos(Math.toRadians(starTangles + angles[i] / 2)) * 15,
                        (float) Math.sin(Math.toRadians(starTangles + angles[i] / 2)) * 15
                );
            }
            canvas.drawArc(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius,
                    getWidth() / 2 + mRadius, getHeight() / 2 + mRadius, starTangles, angles[i], true, paint);
            starTangles += angles[i];
            canvas.restore();
        }
    }
}
