package com.location.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgument
import androidx.navigation.fragment.navArgs
import com.location.base.BaseFragment
import com.location.base.debugLog
import com.location.wanandroid.databinding.FragmentDetailsBinding

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 5:42 PM
 * description：
 */
class DetailsFragment:BaseFragment() {
    private  lateinit var binding:FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugLog("name:${args.name}")
        binding.webView.loadUrl(args.url)
    }

}