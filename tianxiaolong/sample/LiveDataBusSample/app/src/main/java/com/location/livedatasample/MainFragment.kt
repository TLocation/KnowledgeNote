package com.location.livedatasample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveDataBus
import com.location.livedatasample.databinding.FragmentLayoutBinding
import kotlin.random.Random

/**
 *
 * @author tianxiaolong
 * time：2020/6/23 21:13
 * description：
 */
class MainFragment1() : Fragment() {
    private  lateinit var  binding: FragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentTitle.text = "发送方"
        binding.fragmentSendBtn.setOnClickListener {
            LiveDataBus.getDefault().postValue(DATA_KEY,"随机数${Random.nextInt()}")
        }
    }
}