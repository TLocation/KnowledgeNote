package com.sun.customviewmemorysample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *
 * @author tianxiaolong
 * time：2020/6/29 10:33 PM
 * description：
 */
class AnimView @JvmOverloads constructor(context:Context,attr:AttributeSet?=null) :View(context,attr) {
    private  val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }

    private  var centerX = 0f
    private  var centerY = 0f
    private  var radius = 300f
    private  val anim = ValueAnimator.ofFloat(0f,500f)
        .apply {
            duration = 250
            repeatMode = ValueAnimator.REVERSE
            repeatCount = 99999
        }

    init {
        anim.addUpdateListener {
            invalidate()
          radius = it.animatedValue as Float
        }


    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val s = Canvas()
//        val r = RectF()
//        val r1 = RectF()
//        val r2 = RectF()
//        val r4 = RectF()
//        val r5 = RectF()
//        val r31 = RectF()
//        val r32 = RectF()
//        val r33 = RectF()
//        val r37 = RectF()
//        val bitmap = Bitmap.createBitmap(400,400,Bitmap.Config.ARGB_8888)
//        val sd = SweepGradient(0f,0f,Color.RED,Color.BLACK)


        canvas.drawCircle(centerX,centerY,radius,paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerX = measuredWidth/2f
        centerY = measuredHeight/2f
    }

    fun start(){
       anim.start()

    }
}