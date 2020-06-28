package com.location.jetpacksample.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.provider.Settings
import android.view.*
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.location.jetpacksample.LocationListener


/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:54
 * description：
 */
class LifycycleService : Service(),LocationListener {
    private  var addView = false
    private val windowManager: WindowManager by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val layoutParams: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.RGBA_8888
            gravity = Gravity.LEFT or Gravity.TOP
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            width = 500
            height = 100
            x = 0
            y = 0
        }
    }
    private val button: LinearLayout by lazy {
        LinearLayout(this)
            .apply {
                orientation = LinearLayout.VERTICAL
                val contentView = TextView(this@LifycycleService)
                contentView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(Color.BLUE)
                contentView.text = "测试"
                contentView.setTextColor(Color.WHITE)
                addView(contentView)
            }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return LifycycleBinder()
    }

    fun setState(msg:String){
        val textView = button.getChildAt(0) as TextView
        textView.text = msg
    }
    override fun onCreate() {
        super.onCreate()
        setState("onCreate")
    }

    override fun onStart() {
        setState("onStart")
    }

    override fun onResume() {
        setState("onResume")
    }

    override fun onPause() {
        setState("onPause")
    }

    override fun onStop() {
        setState("onStop")
    }

    override fun onActivityDestroy() {
        setState("onActivityDestroy")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId)

    }

    private fun showFloatingWindow() {
        if (Settings.canDrawOverlays(this) && !addView) {
          addView = true
            windowManager.addView(button, layoutParams)
            button.setOnTouchListener(FloatingOnTouchListener())
        }
    }

    public  inner  class LifycycleBinder :Binder(){
        fun getLifycycleOberver():LifecycleObserver = this@LifycycleService
    }
    inner class FloatingOnTouchListener : OnTouchListener {
        private var x = 0
        private var y = 0
        override fun onTouch(view: View?, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    layoutParams.x = layoutParams.x + movedX
                    layoutParams.y = layoutParams.y + movedY
                    windowManager.updateViewLayout(view, layoutParams)
                }
                else -> {
                }
            }
            return false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addView = false
        windowManager.removeView(button)
    }
}