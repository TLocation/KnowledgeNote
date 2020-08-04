package com.location.jetpacksample.viewmodel

import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.location.base.JetpackBaseActivity
import com.location.jetpacksample.databinding.ActivityViewmodelBinding

/**
 *
 * @author tianxiaolong
 * time：2020/7/5 3:33 PM
 * description：
 */
class ViewModelActivity:JetpackBaseActivity() {
    /**
     * SaveStateViewModle 会保存数据状态 即使在后台杀死 数据还会保留
     * OrdinaryViewModel 普通的viewmodle 窗口配置改变后 数据会保留 在后台杀死 数据不会保留
     */
    private  val ordViewModle by viewModels<SaveStateViewModle>()
    private  val androidViewModle by  viewModels<AndroidSmapleViewModele>(factoryProducer = { CustomFactory(application,"test") })
    private val binding by lazy { ActivityViewmodelBinding.inflate(LayoutInflater.from(this)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ordViewModle.numberLiveData.observe(this, Observer {

            binding.viewmodelText.text = "大小:$it"
        })
        binding.viewmodelAdd.setOnClickListener {

            ordViewModle.add()
        }

        binding.viewmodelReduce.setOnClickListener {
            ordViewModle.reduce()
        }
        binding.viewmodelCacheDir.text = "内置缓存地址：${androidViewModle.getCachedir()}"
    }



}

class TestFragment :Fragment(){
    val modle by viewModels<SaveStateViewModle>()
    val m2 by activityViewModels<SaveStateViewModle>()
}