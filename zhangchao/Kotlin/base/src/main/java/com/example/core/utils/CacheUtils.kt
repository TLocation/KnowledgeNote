package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.core.BaseApplication
import com.example.core.R

/**
 *
 * @author zhangchao
 * time：2020/6/20 21:36
 * description：
 */
class CacheUtils{

    companion  object{
        @SuppressLint("StaticFieldLeak")
       var context = BaseApplication.currentApplication

        val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        fun save(key:String , value: String) = SP.edit().putString(key,value)

        fun get(key :String)  =SP.getString(key,null)

    }



}