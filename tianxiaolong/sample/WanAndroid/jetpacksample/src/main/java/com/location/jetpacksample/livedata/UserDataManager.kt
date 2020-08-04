package com.location.jetpacksample.livedata

import android.os.Handler
import androidx.lifecycle.LiveData
import com.location.base.debugLog

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 6:42 PM
 * description：
 * 扩展LiveData 实现用户数据子增长 当有人观察时 数据开始变化 当没有观察时停止
 */
private const val  DELAY_TIME = 1000L

class UserDataManager private constructor():LiveData<UserData>() {

    private val handler by  lazy { Handler() }
    private val userData by lazy { UserData("make",12) }
    private  val runnable = Runnable {
        userData.age+=1
        super.setValue(userData)
        start()
    }

    private fun  start(){
        handler.postDelayed(runnable, DELAY_TIME)
    }

    private fun stop(){
        handler.removeCallbacks(runnable)
    }


    override fun setValue(value: UserData?) {
        throw  IllegalStateException("UserDataManager not support setValue")
    }


    override fun postValue(value: UserData?) {
        throw  IllegalStateException("UserDataManager not support postValue")
    }

    override fun onActive() {
        super.onActive()
        start()
        debugLog("onActive")
    }

    override fun onInactive() {
        super.onInactive()
        debugLog("onInactive")
        stop()
    }

    companion object{
        private  lateinit var  instance:UserDataManager
       fun INSTANCE():UserDataManager {
           instance =  if(::instance.isInitialized) instance else UserDataManager()
           return instance
       }
    }

}