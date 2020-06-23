package com.location.livedatasample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveDataBus
import androidx.lifecycle.Observer
import com.location.livedatasample.databinding.FragmentLayoutBinding

/**
 *
 * @author tianxiaolong
 * time：2020/6/23 21:16
 * description：
 */

class MainFragment2:Fragment() {
    private  lateinit var  binding: FragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentTitle.text = "接收方"
        binding.fragmentSendBtn.setOnClickListener {
            Toast.makeText(requireContext(),"无效按钮",Toast.LENGTH_SHORT).show()
        }
        LiveDataBus.getDefault().observer(DATA_KEY,viewLifecycleOwner, Observer<String> {
            binding.fragmentContent.text = "接收到：$it"
        });
    }
}