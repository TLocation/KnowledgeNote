package com.location.jetpacksample.livedata

import androidx.lifecycle.MutableLiveData

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 6:10 PM
 * description：
 * 二次封装的LiveData 相同的消息不会再次发送
 * distinctUntilChanged
 * 替代方案
 */
class StateLiveData<T>:MutableLiveData<T>() {
    override fun postValue(value: T) {
        if(getValue() != value) {
            super.postValue(value)
        }
    }

    override fun setValue(value: T) {
        if(getValue() != value){
            super.setValue(value)
        }
    }
}