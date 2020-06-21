package com.example.core.utils

import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import com.example.core.BaseApplication

/**
 *
 * @author zhangchao
 * time：2020/6/20 22:45
 * description：
 */
//  private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
//
//    public static float dp2px(float dp) {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
//    }
//
//    public static void toast(String string) {
//        toast(string, Toast.LENGTH_SHORT);
//    }
//
//    public static void toast(String string, int duration) {
//        Toast.makeText(BaseApplication.currentApplication(), string, duration).show();
//    }

 class Utils{

    companion object{

    val displayMetrics = Resources.getSystem().displayMetrics

    fun dp2px(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)

    fun toast(msg: String, duration: Int) {
        Toast.makeText(BaseApplication.currentApplication, msg, duration).show()
    }

    fun toast(msg: String) {
        toast(msg, Toast.LENGTH_SHORT)
    }
    }

}

