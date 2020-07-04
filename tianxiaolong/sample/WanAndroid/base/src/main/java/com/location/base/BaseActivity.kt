package com.location.base

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.location.base.service.LifycycleService

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:32
 * description：
 */

fun AppCompatActivity.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

inline  fun <reified T:Activity> AppCompatActivity.startUiActivity(){
  startActivity(Intent(this,T::class.java))
}

inline  fun <reified  T:Activity> Fragment.startUiActivity(){
    startActivity(Intent(requireContext(),T::class.java))
}
abstract class BaseActivity :AppCompatActivity() {
    private  var isBinding = false
    private  val mConnection =  object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            val binder = service as LifycycleService.LifycycleBinder
            isBinding = true
//            this.lifecycle.
            binder.setName(this@BaseActivity.javaClass.simpleName)
            this@BaseActivity.lifecycle.addObserver(binder.getLifycycleOberver())
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(SharePreferencesUtils.get(OPEN_OBSERVATION_ACTIVITY,false)!!){
            bindService(Intent(this,LifycycleService::class.java), mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isBinding){
            unbindService(mConnection)
        }
    }
}