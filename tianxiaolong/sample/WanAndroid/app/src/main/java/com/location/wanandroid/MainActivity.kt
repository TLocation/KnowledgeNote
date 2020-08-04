package com.location.wanandroid

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.location.base.*
import com.location.jetpacksample.lifycycle.LifecycleActivity
import com.location.jetpacksample.livedata.LiveDataActivity
import com.location.jetpacksample.viewmodel.ViewModelActivity
import com.location.wanandroid.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

//            startUiActivity<ViewModelActivity>()
            lifecycleScope
                .launch {
                    val datas = withContext(Dispatchers.IO){
                        RetrofitUtils.apiService.getArtcleList().data.datas


                    }

                }

        }

    }
}
