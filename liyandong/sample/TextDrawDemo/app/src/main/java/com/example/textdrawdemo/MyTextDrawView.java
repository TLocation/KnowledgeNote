package com.example.textdrawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextDrawView extends View {
    private float mRadius = 300;
    private Paint paint;
    private Paint.FontMetrics fontMetrics;

    public MyTextDrawView(Context context) {
        this(context, null);
    }

    public MyTextDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        fontMetrics = paint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius,
                getWidth() / 2 + mRadius, getHeight() / 2 + mRadius, 0, 360, false, paint);
        paint.setColor(Color.RED);
        paint.setTextSize(100f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        String text = "DrawView";
        float y = (fontMetrics.descent + fontMetrics.ascent) / 2 - fontMetrics.descent;
        Log.d("MyTextDrawView", "onDraw:" + y);
        canvas.drawText(text, getWidth() / 2 - text.length() / 2, getHeight() / 2 - y, paint);
    }
}
