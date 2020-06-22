package com.example.core

import android.app.Application
import android.content.Context

/**
 *
 * @author zhangchao
 * time：2020/6/21 13:20
 * description：
 */
class BaseApplication : Application() {

    companion object{
        lateinit var currentApplication : Context
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
      currentApplication = this
    }
}