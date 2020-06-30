package com.example.core.utils

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * @author tianxiaolong
 * time：2020/6/30 11:09 PM
 * description：
 */
inline  fun SharedPreferences.withSave(block:SharedPreferences.Editor.() -> Unit){
    val edit = edit()
    try {
        edit.block()
    }finally {
        edit.apply()
    }
}
object SharePreferencesUtils {
    private lateinit var  share:SharedPreferences

    fun init(context:Context){
        share = context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)
    }

    fun <T:Any> get(key:String) :T?{
        return  when(T){
            is String -> share.getString(key,null) as T
            is Boolean -> share.getBoolean(key,false) as T
            is Float -> share.getFloat(key,0f) as T
            else -> return null
        }
    }
    fun <T :Any> put(key: String,value:T){
        share.withSave {
            when(value){
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
            }
        }


    }
}