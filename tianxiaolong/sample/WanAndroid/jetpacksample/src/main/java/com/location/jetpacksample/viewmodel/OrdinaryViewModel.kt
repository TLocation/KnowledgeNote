package com.location.jetpacksample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

/**
 *
 * @author tianxiaolong
 * time：2020/7/5 3:30 PM
 * description：
 */
class OrdinaryViewModel :ViewModel() {
    private var isStart  = false
     val numberLiveData = MutableLiveData<Int>()

    fun add(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue + 1
    }

    fun reduce(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue - 1
    }

    override fun onCleared() {
        super.onCleared()
    }
}