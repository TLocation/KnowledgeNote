package com.location.jetpacksample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import com.location.base.toast
import com.location.jetpacksample.databinding.ActivityLifycleBinding
import com.location.jetpacksample.service.LifycycleService

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:41
 * description：
 */
class LifycycleActivity : JetpackBaseActivity() {

private  var isBind = false

    private  val mConnection =  object :ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
           val binder = service as LifycycleService.LifycycleBinder
            this@LifycycleActivity.lifecycle.addObserver(binder.getLifycycleOberver())
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityLifycleBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        inflate.lifycycleBth.setOnClickListener {

            if(checkFloatAuthority()){
                startService(Intent(this,LifycycleService::class.java))

                if(!isBind){
                    bindService(Intent(this,LifycycleService::class.java), mConnection, Context.BIND_AUTO_CREATE)
                }
                isBind = true
            }else{
                startRequestWindowPermission()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(isBind){
            unbindService(mConnection)
            isBind = false
        }
    }
    override fun requestAlertSuccess() {
        super.requestAlertSuccess()
        toast("权限请求成功")
    }

    override fun requestAlertFail() {
        super.requestAlertFail()
        toast("权限请求失败")
    }

}