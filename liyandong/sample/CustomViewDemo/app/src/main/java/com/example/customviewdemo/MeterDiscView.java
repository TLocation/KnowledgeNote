package com.example.customviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MeterDiscView extends View {

    private Paint paint;
    private Path path;
    private PathEffect pathEffect;
    private Path arcPath;
    private float remainAngle = 120;
    private float mRadius = 300;
    private int scaLength = 200;
    private Path textPath;

    public MeterDiscView(Context context) {
        this(context, null);
    }

    public MeterDiscView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeterDiscView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        path = new Path();
        path.addRect(0, 0, 10, 50, Path.Direction.CCW);
        arcPath = new Path();
        arcPath.addArc(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius, getWidth() / 2 + mRadius, getHeight() / 2 + mRadius,
                90 + remainAngle / 2, 360 - remainAngle);
        PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        pathEffect = new PathDashPathEffect(this.path, (pathMeasure.getLength() - 10) / 10, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius, getWidth() / 2 + mRadius, getHeight() / 2 + mRadius,
                90 + remainAngle / 2, 360 - remainAngle, false, paint);
        paint.setPathEffect(pathEffect);
        canvas.drawArc(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius, getWidth() / 2 + mRadius, getHeight() / 2 + mRadius,
                90 + remainAngle / 2, 360 - remainAngle, false, paint);
        paint.setPathEffect(null);
        float cos = (float) Math.cos(Math.toRadians(90 + remainAngle / 2 + (360 - remainAngle) / 10 * 4));
        float sin = (float) Math.sin(Math.toRadians(90 + remainAngle / 2 + (360 - remainAngle) / 10 * 4));
        canvas.drawLine(getWidth() / 2, getHeight() / 2, cos * scaLength + getWidth() / 2, sin * scaLength + getHeight() / 2, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50f);
        for (int i = 0; i < 15; i++) {
            String s = String.valueOf(i);
            float cosText = (float) Math.cos(Math.toRadians(90 + remainAngle / 2 + (360 - remainAngle) / 10 * i));
            float sinText = (float) Math.sin(Math.toRadians(90 + remainAngle / 2 + (360 - remainAngle) / 10 * i));
            canvas.drawText(s, cosText * 210 + getWidth() / 2, sinText * 210 + getHeight() / 2, paint);
        }
    }
}
