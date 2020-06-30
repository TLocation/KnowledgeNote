package com.location.jetpacksample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import com.location.base.JetpackBaseActivity
import com.location.base.toast
import com.location.jetpacksample.databinding.ActivityLifycleBinding
import com.location.base.service.LifycycleService

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:41
 * description：
 */
class LifycycleActivity : JetpackBaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityLifycleBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        inflate.lifycycleBth.setOnClickListener {


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