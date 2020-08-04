package com.example.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PracticeView extends View {

    private Paint mPaint, mPaintOne;
    float[] pointFloat = new float[]{
            600, 550,
            600, 600,
            600, 650
    };
    float[] lineFloat = new float[]{
            50 * 2, 50 * 2, 100 * 2, 50 * 2,
            100 * 2, 50 * 2, 100 * 2, 150 * 2,
            100 * 2, 150 * 2, 150 * 2, 150 * 2,
            150 * 2, 50 * 2, 150 * 2, 100 * 2,
            150 * 2, 100 * 2, 50 * 2, 100 * 2,
            50 * 2, 100 * 2, 50 * 2, 150 * 2
    };
    private Path path;
    private Bitmap bitmap;
    private RectF rectF2;
    private RectF rectF3;
    private Rect rect;
    private PathEffect pathEffect;
    private Shader shaderGrad;
    private Shader shaderLinear;
    private Shader shader;
    private Shader shaderBitmap;
    private ColorFilter lightingColorFilter;
    private BlurMaskFilter blurMaskFilter;

    public PracticeView(Context context) {
        super(context);
    }

    public PracticeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaintOne = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充

        mPaint.setStrokeWidth(10f);   //设置线条宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置线头的形状
        mPaint.setStrokeJoin(Paint.Join.BEVEL);  //设置拐角的形状。

        mPaint.setDither(true); // 设置图像的抖动。
        mPaint.setFilterBitmap(true);   //设置是否使用双线性过滤来绘制 Bitmap 。

        //mPaintOne.setColor(Color.MAGENTA);       //设置画笔颜色
        mPaintOne.setStyle(Paint.Style.FILL);  //设置画笔模式为扫边
        mPaintOne.setStrokeWidth(10f);
        path = new Path();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cat1);
        pathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);

        //设置扫描渐变
        shaderGrad = new SweepGradient(970, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        rect = new Rect(400, 50, 600, 250);
        rectF3 = new RectF(100, 400, 500, 800);
        //线性渐变
        shaderLinear = new LinearGradient(400, 10, 500, 200, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        //给paint设置辐射渐变
        rectF2 = new RectF(600, 50, 800, 100);
        shader = new RadialGradient(800, 70, 100, Color.parseColor("#E91d63"),
                Color.parseColor("#21D6F3"), Shader.TileMode.CLAMP);
        //用bitmap来给图形着色
        shaderBitmap = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给图片模拟简单的光照效果的。
        lightingColorFilter = new LightingColorFilter(0xffffff, 0x003000);
        //设置图片模糊效果
        blurMaskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
    }

    public PracticeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE); //绘制蓝色
        canvas.drawPoints(pointFloat, mPaint); //绘制点
        mPaint.setPathEffect(pathEffect); //设置用虚线来绘制线条
        canvas.drawLines(lineFloat, mPaint); //绘制线


        mPaintOne.setShader(shaderGrad);
        canvas.drawCircle(950, 100, 100, mPaintOne); //绘制圆


        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF3, mPaint);// 绘制背景矩形
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF3, 0, 90, true, mPaint); // 绘制圆弧

        mPaint.setTextSize(100);
        mPaint.setColor(Color.BLACK);
        mPaint.setShadowLayer(10, 0, 0, Color.GREEN);  //给字体设置底部阴影效果
        canvas.drawText("hello", 200, 600, mPaint);


        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setShader(shaderLinear);
        canvas.drawRect(rect, mPaint); //绘制矩形

        //canvas.scale(0.5f, 0.5f);  //画布缩放
        //canvas.rotate(180, 200, 0);  //旋转180度 <-- 旋转中心向右偏移200个单位
        //canvas.skew(1,0);  //画布错切
        //mPaint.setColor(Color.BLACK);
        //canvas.drawRect(rect, mPaint);


        mPaint.setShader(shader);
        canvas.drawOval(rectF2, mPaint); //绘制椭圆


        mPaint.setShader(shaderBitmap);

        mPaint.setColorFilter(lightingColorFilter);
        canvas.drawCircle(750, 600, 200, mPaint);


        mPaint.setMaskFilter(blurMaskFilter);
        canvas.drawBitmap(bitmap, 100, 900, mPaint); //绘制图片

        //绘制path
        path.addArc(200, 900, 400, 1100, -225, 225);
        path.arcTo(400, 900, 600, 1100, -180, 225, false);
        path.lineTo(400, 1000);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, mPaint);
    }
}
