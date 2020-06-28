package com.location.base

import android.util.Log

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 20:32
 * description：
 */
const val TAG = "WanAndroid"

fun Any.debugLog(msg: String) {
    if (BuildConfig.DEBUG) Log.d(TAG, "[$msg]")
}

fun Any.releaseLog(msg: String) {
    Log.i(TAG, "[$msg]")
}