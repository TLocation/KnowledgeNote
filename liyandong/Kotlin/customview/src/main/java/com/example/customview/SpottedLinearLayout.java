package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SpottedLinearLayout extends LinearLayout {

    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    public SpottedLinearLayout(Context context) {
        super(context);
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint1 = new Paint();
        paint1.setColor(Color.DKGRAY);
        paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint3 = new Paint();
        paint3.setColor(Color.MAGENTA);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.drawCircle(300, 300, 10, paint);
        canvas.drawCircle(400, 350, 22, paint1);
        canvas.drawCircle(500, 450, 50, paint2);
        canvas.drawCircle(700, 600, 35, paint3);
    }
}
