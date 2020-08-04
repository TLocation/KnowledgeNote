package com.location.jetpacksample.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.location.jetpacksample.databinding.FragmentReceiveLivedataBinding
import com.location.jetpacksample.databinding.FragmentSendLivedataBinding

/**
 *
 * @author tianxiaolong
 * time：2020/7/4 5:20 PM
 * description：
 */
class LiveDataReceiveFragment :Fragment(){
    private  lateinit var binding:FragmentReceiveLivedataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReceiveLivedataBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ( activity as LiveDataActivity).liveData.observe(viewLifecycleOwner, Observer {
            binding.fragmentReceiveTxt.text = it
        })
    }
}