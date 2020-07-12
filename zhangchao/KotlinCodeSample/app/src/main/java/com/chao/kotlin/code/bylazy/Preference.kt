package com.chao.kotlin.code.bylazy

import android.content.Context
import android.content.SharedPreferences
import com.chao.kotlin.code.App
import kotlin.reflect.KProperty

/**
 *
 * @author zhangchao
 * time：2020/7/9 22:41
 * description：
 */
class Preference<T>(val key : String ,private val value : T?) {
    private val prefs : SharedPreferences by lazy {
        App.instance.applicationContext.getSharedPreferences("app",Context.MODE_PRIVATE)
    }

    operator fun getValue(t: T?, property: KProperty<*>): T {

        return getSp(key,value)
    }

    operator fun setValue(t: T?, property: KProperty<*>, e: T) {
         putSp(key,e)

    }



  private  fun putSp(key : String ,value : T ) =
      with(prefs.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw  RuntimeException("类型不对")

            }.apply()
        }


    private fun getSp( key : String ,default : T?) : T = with(prefs){
        var resut : Any = when(default){
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")

        }

        return resut as T
    }



}