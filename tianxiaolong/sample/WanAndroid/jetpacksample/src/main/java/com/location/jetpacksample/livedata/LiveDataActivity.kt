package com.location.jetpacksample.livedata

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.location.base.JetpackBaseActivity
import com.location.jetpacksample.R

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 3:11 PM
 * description：
 */
class LiveDataActivity : JetpackBaseActivity() {
    val liveData by lazy { MutableLiveData<String>() }
    val stateLiveData by lazy { StateLiveData<Boolean>() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata_sample)
        supportFragmentManager.commit {
            add(R.id.livedata_view1, LiveDataSendFragment())
        }
        stateLiveData.observe(this, Observer {
            if(it){
                addFragment2()
            }
        })
    }

    private  fun addFragment2(){
        supportFragmentManager.commit {
            add(R.id.livedata_view2, LiveDataReceiveFragment())
        }
    }
}