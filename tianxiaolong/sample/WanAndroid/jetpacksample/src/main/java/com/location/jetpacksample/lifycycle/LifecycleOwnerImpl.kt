package com.location.jetpacksample.lifycycle

import androidx.lifecycle.*
import com.location.base.debugLog

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 3:33 PM
 * description：
 */

class LifecycleOwnerImpl:LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
       debugLog("onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        debugLog("onStart")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
debugLog("onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){
        debugLog("onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        debugLog("onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        debugLog("onDestroy")
    }







}