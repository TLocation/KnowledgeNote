package com.location.jetpacksample.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.random.Random

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 10:26 PM
 * description：
 */
private fun getUserId(): LiveData<Int> {
    val data = MutableLiveData<Int>()
    Thread(Runnable {
        Thread.sleep(100)
        data.postValue(Random.nextInt())
    }).start()
    return data
}

class LiveDataExtendViewModel : ViewModel() {
    val userDataManager by lazy { UserDataManager.INSTANCE() }

    val userAge = Transformations.map(userDataManager) {
        when (it.age) {
            in 0..7 -> "小孩"
            in 8..18 -> "青少年"
            in 19..30 -> "奋斗青年"
            in 31..50 -> "养育孩子中"
            in 51..80 -> "养老"
            else -> "已入土"
        }
    }

     val userId = Transformations.switchMap(userDataManager) {
        getUserId()
    }

}