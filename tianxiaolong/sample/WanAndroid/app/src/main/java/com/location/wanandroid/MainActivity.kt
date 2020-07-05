package com.location.wanandroid

import android.os.Bundle
import android.view.LayoutInflater
import com.location.base.*
import com.location.jetpacksample.lifycycle.LifecycleActivity
import com.location.jetpacksample.livedata.LiveDataActivity
import com.location.jetpacksample.viewmodel.ViewModelActivity
import com.location.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        inflate.mainOpenLufycycle.setOnClickListener {
              startUiActivity<LifecycleActivity>()
        }
        inflate.mainOpenSettings.setOnClickListener {
            startUiActivity<SettingsActivity>()
        }
        inflate.mainOpenLivedata.setOnClickListener {
            startUiActivity<LiveDataActivity>()
        }
        inflate.mainOpenViewmodel.setOnClickListener {

            startUiActivity<ViewModelActivity>()
        }
    }
}
