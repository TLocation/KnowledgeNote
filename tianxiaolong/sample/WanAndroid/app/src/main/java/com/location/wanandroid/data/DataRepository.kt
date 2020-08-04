package com.location.wanandroid.data

import androidx.lifecycle.LiveData
import com.location.wanandroid.databean.ArticleData

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 4:37 PM
 * description：
 */
interface DataRepository {
    suspend fun  getArticleList():ArticleData



}