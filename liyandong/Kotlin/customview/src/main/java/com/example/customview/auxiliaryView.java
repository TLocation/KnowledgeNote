package com.example.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AuxiliaryView extends View {

    private Paint mPaint;
    private Bitmap imageBitmap;
    private Path path;
    private float[] rids = new float[]{10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};
    private Camera camera;
    private Matrix matrix;
    private ValueAnimator animator;
    private float degree;

    public AuxiliaryView(Context context) {
        super(context);
    }

    public AuxiliaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        camera = new Camera();
        matrix = new Matrix();
        imageBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yh0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        int left = 100;
//        int top = 100;
//        int right = 500;
//        int bottom = 500;
        //裁剪
        //canvas.clipRect(left, top, right, bottom);

        //根据path裁剪图片
//        path = new Path();
//        int w = this.getWidth();
//        int h = this.getHeight();
//        path.addRoundRect(new RectF(15, 20, w, h), rids, Path.Direction.CW);
//        canvas.clipPath(path);

        // 平移 translate  旋转 rotate   缩放 scale    错切  skew
        //canvas.skew(0,0.5f);

        //根据matrix来变换
//        Matrix matrix = new Matrix();
//        matrix.reset();
//        matrix.postTranslate();
//        matrix.postRotate();
//        canvas.concat(matrix);  //调用
        // 使用setPolyToPoly 自定义变化
        //使用Camera 进行三维变换   Camera 也需要 保存和恢复状态才能正常绘制
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        canvas.save();
        camera.save();
        camera.rotateX(degree); // 旋转 Camera 的三维空间  x轴旋转30度
        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();
        matrix.postScale(measuredWidth / width, measuredHeight / height);
        camera.getMatrix(matrix);
//        canvas.translate(measuredWidth / 2, measuredHeight / 2); // 旋转之后把投影移动回来
//        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
//        canvas.translate(-measuredWidth / 2, measuredHeight / 2); // 旋转之前把绘制内容移动到轴心（原点）
//        camera.setLocation(0, 0, 50f);
        camera.restore();
        canvas.drawBitmap(imageBitmap, matrix, mPaint);
        canvas.restore();
    }

    public void startAnimation() {
        animator = ValueAnimator.ofFloat(0, 90);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                degree = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
