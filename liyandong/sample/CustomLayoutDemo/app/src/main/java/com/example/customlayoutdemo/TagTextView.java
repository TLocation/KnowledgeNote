package com.example.customlayoutdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Random;

public class TagTextView extends AppCompatTextView {
    private int[] ColorArray = {Color.parseColor("#2E8B57"),
            Color.parseColor("#800080"),
            Color.parseColor("#ADFF2F"),
            Color.parseColor("#9932CC"),
            Color.parseColor("#6B8E23"),
            Color.parseColor("#90EE90"),
            Color.parseColor("#FF00FF")
    };
    private int[] TextSize = {16, 18, 20};
    private int mTitleTextSize = 20;
    private int mCornerSize = 6;
    private Paint paint;

    public TagTextView(Context context) {
        this(context, null);
    }

    public TagTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public TagTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setTextSize(mTitleTextSize);
        paint.setColor(ColorArray[new Random().nextInt(ColorArray.length)]);
        setTextColor(Color.WHITE);
        setPadding(16, 8, 16, 8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), mCornerSize, mCornerSize, paint);
        super.onDraw(canvas);
    }
}
