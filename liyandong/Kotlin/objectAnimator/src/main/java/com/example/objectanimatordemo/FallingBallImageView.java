package com.example.objectanimatordemo;


import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class FallingBallImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int left = 0;
    private int top = 0;
    private int bottom;
    private int right;

    public FallingBallImageView(Context context) {
        super(context);
    }

    public FallingBallImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FallingBallImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //left top 只赋值一次
        if (this.left != 0) {
            return;
        }
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setFallingPos(Point point) {
        //从起始位置开始
        layout(left + point.x, top + point.y, point.x + right, point.y + bottom);
    }
}
