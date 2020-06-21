package com.example.core.utils

import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

object Utils {
    private val displayMetrics = Resources.getSystem().displayMetrics
    fun dp2px(float: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, float, displayMetrics)
    }

    fun toast(string: String) {
        toast(string, Toast.LENGTH_SHORT)
    }

    private fun toast(string: String, duration: Int) {
        Toast.makeText(BaseApplication.currentApplication, string, duration).show()
    }
}