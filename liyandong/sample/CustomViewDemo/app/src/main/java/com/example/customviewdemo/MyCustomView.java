package com.example.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View {

    private Paint paint;
    private Path path;
    private String text;
    private Rect bounds;

    public MyCustomView(Context context) {
        this(context, null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCustomView, R.attr.MyCustomViewDefStyleAttr, R.style.MyCustomViewDefStyleRes);
        String attr1 = typedArray.getString(R.styleable.MyCustomView_custom_attr1);
        String attr2 = typedArray.getString(R.styleable.MyCustomView_custom_attr2);
        String attr3 = typedArray.getString(R.styleable.MyCustomView_custom_attr3);
        String attr4 = typedArray.getString(R.styleable.MyCustomView_custom_attr4);
        Log.e("MyCustomView---", "MyCustomView: " + attr1);
        Log.e("MyCustomView---", "MyCustomView: " + attr2);
        Log.e("MyCustomView---", "MyCustomView: " + attr3);
        Log.e("MyCustomView---", "MyCustomView: " + attr4);
        typedArray.recycle();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
        bounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(50);
        canvas.drawText("Hello everyOne", 50, 100, paint);
        path.moveTo(700, 100);
        path.quadTo(400, 100, 500, 300);
        canvas.drawPath(path, paint); // 把 Path 也绘制出来，理解起来更方便

        canvas.drawTextOnPath("Hello everyOne", path, 0, 0, paint);
        canvas.drawText("Hello everyOne", 400, 900, paint);
        text = "Hello everyOne";

        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.left += 200;
        bounds.top += 500;
        bounds.right += 200;
        bounds.bottom += 500;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);
        paint.setFakeBoldText(true);  //设置粗体
        paint.setStrikeThruText(true); //设置加删除线
        paint.setUnderlineText(true); //设置加下划线
        paint.setLetterSpacing(0.2f); //设置间距
        paint.setTextSize(70);
        canvas.drawText("Hello everyOne", 200, 500, paint);
        text = "Hello everyOne";

        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.left += 200;
        bounds.top += 500;
        bounds.right += 200;
        bounds.bottom += 500;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);

        path.moveTo(200, 0);
        path.lineTo(318, 362);
        path.lineTo(10, 138);
        path.lineTo(390, 138);
        path.lineTo(82, 362);
        path.close();
        canvas.drawPath(path, paint);

        for (int i = 0; i < 10; i++) {
            path.moveTo(600, 1200 + i * 10);
            path.lineTo(700, 1200 + i * 10);
            path.lineTo(800 - i * 10, 1300);
            path.lineTo(700, 1400 - i * 10);
            path.lineTo(600, 1400 - i * 10);
            path.lineTo(500 + i * 10, 1300);
            path.close();
            canvas.drawPath(path, paint);
        }
    }
}
