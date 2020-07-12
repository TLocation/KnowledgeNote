package com.location.wanandroid.data

import android.content.Context
import com.location.wanandroid.model.ArticleViewModelFactory

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 5:12 PM
 * description：
 */
object InjetUtils {

    fun getArticleFactory(context:Context):ArticleViewModelFactory{
        return  ArticleViewModelFactory(DefaultDataRepository(context))
    }
}