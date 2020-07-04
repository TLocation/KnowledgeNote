package com.location.base

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 20:32
 * description：
 */
const val TAG = "WanAndroid"
fun Any.debugLog(msg: String) {
    if (BuildConfig.DEBUG) Log.d(TAG, "[${javaClass.simpleName}]:[$msg]")
}

fun Any.releaseLog(msg: String) {
    Log.i(TAG, "[${javaClass.simpleName}]:[$msg]")
}

private val displayMetrics: DisplayMetrics? = Resources.getSystem().displayMetrics

fun Float.dp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)