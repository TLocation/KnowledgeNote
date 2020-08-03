package com.chao.kotlin.code

import android.app.Application

/**
 *
 * @author zhangchao
 * time：2020/7/9 22:38
 * description：
 */
class App : Application() {

    companion object{
        lateinit var instance : App
       private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}