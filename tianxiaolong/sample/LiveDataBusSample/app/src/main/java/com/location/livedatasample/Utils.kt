package com.sun.android.util

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 *
 * @author tianxiaolong
 * time：2019/12/2 19:24
 * description：
 */
fun Context.startActivity(clazz: Class<out Activity>) {
    this.startActivity(Intent(this, clazz))
}


