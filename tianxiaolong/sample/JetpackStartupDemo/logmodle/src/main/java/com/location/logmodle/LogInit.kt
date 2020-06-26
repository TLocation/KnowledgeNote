package com.location.logmodle

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.location.networkmodle.NetWorkInit
import com.location.networkmodle.debugMsg

/**
 *
 * @author zhangchao
 * time：2020/6/26 21:47
 * description：
 */

class LogInit : Initializer<String> {
    override fun create(context: Context): String {
        debugMsg("LogInit init create")
         return  "value"
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        debugMsg("LogInit init dependencies")
       return mutableListOf(NetWorkInit::class.java)
    }
}