package com.location.networkmodle

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 *
 * @author zhangchao
 * time：2020/6/26 22:38
 * description：
 */
fun Any.debugMsg(msg:String){
    Log.i("TestStartup",msg)
}
class NetWorkInit:Initializer<String>{
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
     return mutableListOf()
    }

    override fun create(context: Context): String {
        debugMsg("NetWorkInit Initializer")
        return "networkInit"
    }

}