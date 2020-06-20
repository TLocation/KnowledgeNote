package com.example.core.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

/**
 *
 * @author tianxiaolong
 * time：2020/6/20 8:48 PM
 * description：
 */
private val displayMetrics: DisplayMetrics? = Resources.getSystem().displayMetrics

fun Float.dp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)


fun toast(string: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.currentApplication, string, duration).show()
}