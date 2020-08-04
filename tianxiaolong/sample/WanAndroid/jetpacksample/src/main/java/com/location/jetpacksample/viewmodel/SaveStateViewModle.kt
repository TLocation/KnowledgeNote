package com.location.jetpacksample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 *
 * @author tianxiaolong
 * time：2020/7/5 4:20 PM
 * description：
 */
private  const val key = "key_number"
class SaveStateViewModle(private  val savedStateHandle: SavedStateHandle) :
  ViewModel() {
    val numberLiveData = MutableLiveData<Int>()
    init {
        if(savedStateHandle.contains(key)){
            numberLiveData.value = savedStateHandle[key]
        }

    }


    fun add(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue + 1
        savedStateHandle[key] = numberLiveData.value
    }

    fun reduce(){
        val oldValue = numberLiveData.value?:0
        numberLiveData.value = oldValue - 1
        savedStateHandle[key] = numberLiveData.value
    }
}