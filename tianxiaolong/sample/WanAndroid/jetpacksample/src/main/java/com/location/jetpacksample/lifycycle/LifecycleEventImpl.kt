package com.location.jetpacksample.lifycycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.location.base.debugLog

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 3:46 PM
 * description：
 */
class LifecycleEventImpl:LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        debugLog("event=${event.name}")
    }
}