package com.location.wanandroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 4:13 PM
 * description：
 */
object RetrofitUtils {
    private const val BASE_URL = "https://www.wanandroid.com/"
    val apiService by  lazy {
        val build = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()
        build.create(Api::class.java)
    }
}