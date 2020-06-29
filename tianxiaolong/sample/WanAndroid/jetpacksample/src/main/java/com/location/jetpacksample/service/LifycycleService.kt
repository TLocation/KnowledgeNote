package com.location.jetpacksample.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.provider.Settings
import android.util.LruCache
import android.view.*
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.lruCache
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.location.base.debugLog
import com.location.jetpacksample.LocationListener
import org.w3c.dom.NodeList
import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random


/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:54
 * description：
 */
class LifycycleService : Service(),LocationListener {
    private  var cacheData: ActitivData? = null
    private  var cacheCount = 0
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
            height = 600
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

    fun setState(msg:String,key:Int){
        if(cacheData == null){
            cacheData = ActitivData(msg)
            cacheCount+=1
        }else{
             if(cacheCount==5){
                 cacheData = cacheData?.next
                 var tempCache:ActitivData = cacheData!!.next!!
                 while (tempCache.next!=null){
                     tempCache = tempCache.next!!
                 }
                 tempCache.next = ActitivData(msg)

             }else{
                 var tempData:ActitivData = cacheData!!
                 while (tempData.next!=null){
                     tempData = tempData.next!!
                 }
                 tempData.next = ActitivData(msg)
                 cacheCount+=1
             }
        }
        val textView = button.getChildAt(0) as TextView
        val builder = StringBuilder()
        var tempData = cacheData
        while (tempData!=null){
            builder.append(tempData.msg!!).append("\r\n")
            tempData = tempData.next
        }


        textView.text = builder.toString()
    }


    override fun onActivityCreate() {
        debugLog("onCreate")
        setState("onCreate",1)
    }

    override fun onStart() {
        setState("onStart",2)
    }

    override fun onResume() {
        setState("onResume",3)
    }

    override fun onPause() {
        setState("onPause",4)
    }

    override fun onStop() {
        setState("onStop",5)
    }

    override fun onActivityDestroy() {
        setState("onActivityDestroy",6)
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