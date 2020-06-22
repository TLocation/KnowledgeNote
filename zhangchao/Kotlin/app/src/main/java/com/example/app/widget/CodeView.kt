package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.app.R
import com.example.core.utils.Utils
import kotlin.random.Random


/**
 *
 * @author zhangchao
 * time：2020/6/20 21:50
 * description：
 */
class CodeView : AppCompatTextView {
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attar: AttributeSet) : super(context, attar) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }



    init{

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        gravity = Gravity.CENTER
        setBackgroundColor(getContext().getColor(R.color.colorPrimary))
        setTextColor(Color.WHITE)
            var  point  = Point()
            paint.style = Paint.Style.STROKE
            paint.color = getContext().getColor(R.color.colorAccent)
            paint.strokeWidth = Utils.dp2px(6f)


    }
    var list  = arrayOf( "kotlin",
            "android",
            "java",
            "http",
            "https",
            "okhttp",
            "retrofit",
            "tcp/ip")


    fun updateCode() {
        val random =Random.nextInt(list.size)
        val code = list[random]
        setText(code)
    }



    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        super.onDraw(canvas)
    }



}