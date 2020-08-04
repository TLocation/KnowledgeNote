package com.location.jetpacksample.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @author tianxiaolong
 * time：2020/7/5 4:37 PM
 * description：
 */
@Suppress("UNCHECKED_CAST")
class CustomFactory(private val application: Application, val name:String):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AndroidSmapleViewModele::class.java)) return  AndroidSmapleViewModele(application,name)  as T
        return super.create(modelClass)
    }
}