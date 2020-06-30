package com.location.base

import android.content.Context
import android.content.SharedPreferences
import androidx.startup.Initializer

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

class SharePreferencesInit:Initializer<SharedPreferences>{
    override fun create(context: Context): SharedPreferences {
        SharePreferencesUtils.init(context)
        return  SharePreferencesUtils.share
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}
object SharePreferencesUtils {
    internal lateinit var  share:SharedPreferences

  internal  fun init(context:Context){
        share = context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)
    }

    fun <T:Any> get(key:String,default:T?) :T?{
        return  when(default){
            is String -> share.getString(key,default)  as T
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