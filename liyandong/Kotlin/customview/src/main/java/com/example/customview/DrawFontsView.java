package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawFontsView extends View {

    private Paint paint;

    public DrawFontsView(Context context) {
        super(context);
    }

    public DrawFontsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);       //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        paint.setStrokeWidth(10f);
    }

    public DrawFontsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = "Hello World";
        canvas.drawText(text, 100, 50, paint);
    }
}
