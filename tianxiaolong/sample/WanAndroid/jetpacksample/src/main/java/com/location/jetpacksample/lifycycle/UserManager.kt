package com.location.jetpacksample.lifycycle

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.location.base.debugLog

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 4:06 PM
 * description：
 * 模拟获取用户信息类
 * 这里假设有一个计时器 当activity在前台时开始计时 退到后台时停止
 */

object UserManager : LifecycleObserver {
    private const val DELAY_TIMER = 1000L
    var timer = 0
        private set

    val runnable = Runnable {

        timer += 1
        debugLog("current timer=$timer")
        startRunnable()
    }

    fun startRunnable() {
        handler.postDelayed(runnable, DELAY_TIMER)
    }

    val handlerThread = HandlerThread("UserManager")
    lateinit var handler: Handler

    fun register(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        debugLog("start timer")

        handler.postDelayed(runnable, DELAY_TIMER)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        debugLog("stop timer")
        handler.removeCallbacks(runnable)


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        handlerThread.quitSafely()
    }
}