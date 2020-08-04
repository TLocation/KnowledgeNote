package com.location.wanandroid.data

import android.content.Context
import com.location.wanandroid.RetrofitUtils
import com.location.wanandroid.databean.ArticleData

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 4:39 PM
 * description：
 */
class DefaultDataRepository(val context:Context) :DataRepository {
    override suspend fun getArticleList(): ArticleData {
       return RetrofitUtils.apiService.getArtcleList()
    }
}