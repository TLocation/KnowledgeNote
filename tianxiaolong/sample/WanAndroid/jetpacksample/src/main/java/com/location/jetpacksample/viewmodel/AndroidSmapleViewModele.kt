package com.location.jetpacksample.viewmodel

import android.app.Application
import android.content.Context
import android.util.DebugUtils
import androidx.lifecycle.AndroidViewModel
import com.location.base.debugLog

/**
 *
 * @author tianxiaolong
 * time：2020/7/5 4:16 PM
 * description：
 */
class AndroidSmapleViewModele(application: Application,name:String):AndroidViewModel(application) {
   init {

       debugLog("name=$name")
   }
    fun getCachedir():String{
        return (getApplication() as Context).cacheDir.absolutePath
    }

    override fun onCleared() {
        super.onCleared()
    }
}