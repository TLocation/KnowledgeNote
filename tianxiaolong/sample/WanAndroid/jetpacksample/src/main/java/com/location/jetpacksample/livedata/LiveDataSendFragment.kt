package com.location.jetpacksample.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.location.base.startUiActivity
import com.location.jetpacksample.databinding.FragmentSendLivedataBinding

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 5:20 PM
 * description：
 */
class LiveDataSendFragment :Fragment(){
    private  lateinit var binding:FragmentSendLivedataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendLivedataBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment1SendBtn.setOnClickListener {
            /**
             * 查看LiveData setValue postValue
             * setValue 主线程调用
             * postValue 子线程调用
             * 下面时postValue主要源码 通过这个函数调用到
             * ArchTaskExecutor.getInstance().postToMainThread
             */
            ( activity as LiveDataActivity).liveData.value = binding.fragmentEdittext.text.toString()
            binding.fragmentEdittext.text = null
        }
        binding.fragment2AddFragment.setOnClickListener {
            ( activity as LiveDataActivity).stateLiveData.setValue(true)
        }
        binding.fragment2StartExtend.setOnClickListener {
            startUiActivity<LiveDataExtendActivity>()
        }
    }
}