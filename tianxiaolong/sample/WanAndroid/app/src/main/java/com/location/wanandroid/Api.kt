package com.location.wanandroid

import com.location.wanandroid.databean.ArticleData
import retrofit2.http.GET

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 3:47 PM
 * description：
 */
interface Api {
    @GET("article/list/0/json")
    suspend fun  getArtcleList():ArticleData

}