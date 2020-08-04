package com.location.base

import android.content.Context
import android.content.Intent
import androidx.startup.Initializer
import com.location.base.service.LifycycleService

/**
 *
 * @author tianxiaolong
 * time：2020/6/30 11:58 PM
 * description：
 */
class InitApp:Initializer<Boolean> {
    override fun create(context: Context): Boolean {
       if(SharePreferencesUtils.get(OPEN_OBSERVATION_ACTIVITY,false)!!){
           context.startService(Intent( context,LifycycleService::class.java))
           return true
       }
        return false
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(SharePreferencesInit::class.java)
    }

}