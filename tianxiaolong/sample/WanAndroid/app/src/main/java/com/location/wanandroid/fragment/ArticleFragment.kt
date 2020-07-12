package com.location.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.location.base.BaseFragment
import com.location.wanandroid.ArticleAdapter
import com.location.wanandroid.data.InjetUtils
import com.location.wanandroid.databean.DataX
import com.location.wanandroid.databinding.FragmentArticleBinding
import com.location.wanandroid.model.ArticleViewModel

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 3:46 PM
 * description：
 */
class ArticleFragment:BaseFragment() {

    fun itemClick(data:DataX){

        findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToDetailsFragment(data.link))
    }

    lateinit var  binding:FragmentArticleBinding
    private  val adapter by lazy { ArticleAdapter(
        itemClick = ::itemClick
    ) }
    private val model by viewModels<ArticleViewModel> { InjetUtils.getArticleFactory(requireContext().applicationContext) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listView.adapter = adapter
        model.listData.observe(viewLifecycleOwner,Observer {value ->
            adapter.submitList(value)
        })
    }

}