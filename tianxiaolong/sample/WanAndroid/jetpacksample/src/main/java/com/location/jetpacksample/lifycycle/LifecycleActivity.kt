package com.location.jetpacksample.lifycycle

import android.os.Bundle
import android.view.LayoutInflater
import com.location.base.JetpackBaseActivity
import com.location.base.toast
import com.location.jetpacksample.databinding.ActivityLifycleBinding

/**
 *
 * @author tianxiaolong
 * time：2020/6/28 21:41
 * description：
 */
class LifecycleActivity : JetpackBaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityLifycleBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        //注册生命周期回调
        lifecycle.addObserver(LifecycleOwnerImpl())
        //注册生命周期事件回调
        lifecycle.addObserver(LifecycleEventImpl())
        UserManager.register(this)

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