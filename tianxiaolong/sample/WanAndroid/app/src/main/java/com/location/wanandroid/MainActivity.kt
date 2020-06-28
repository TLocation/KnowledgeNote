package com.location.wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.location.base.BaseActivity
import com.location.base.startUiActivity
import com.location.jetpacksample.LifycycleActivity
import com.location.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
        inflate.mainOpenLufycycle.setOnClickListener {
              startUiActivity<LifycycleActivity>()
        }
    }
}
