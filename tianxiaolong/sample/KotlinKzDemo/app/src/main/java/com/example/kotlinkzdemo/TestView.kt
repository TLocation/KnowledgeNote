package com.example.kotlinkzdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.core.graphics.withSave
import androidx.fragment.app.FragmentManager


/**
 * 没有效果 啥都没有 只为测试高阶函数
 */
inline fun Canvas.saveFunction(s:Canvas.() -> Unit){
    save()
    s()
    restore()
}

inline  fun FragmentManager.addTask(s:() -> Unit){
    val beginTransaction = beginTransaction()
    s.invoke()
    beginTransaction.commit()

}
class TestView(context:Context) :View(context,null,0) {

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        //-------
        canvas.drawColor(Color.BLACK)
        canvas.restore()
        canvas.save()
        //-------
        canvas.drawColor(Color.BLACK)
        canvas.restore()
        canvas.save()
        //-------
        canvas.drawColor(Color.BLACK)
        canvas.restore()
        canvas.save()
        //-------
        canvas.drawColor(Color.BLACK)
        canvas.restore()


        canvas.saveFunction {

            drawColor(Color.BLACK)
        }
        canvas.saveFunction {
            canvas.drawColor(Color.BLACK)
        }
        canvas.saveFunction {
            canvas.drawColor(Color.BLACK)
        }
        canvas.saveFunction {
            canvas.drawColor(Color.BLACK)
        }
        canvas.saveFunction {
            canvas.drawColor(Color.BLACK)
        }
        canvas.withSave {

        }
    }
}