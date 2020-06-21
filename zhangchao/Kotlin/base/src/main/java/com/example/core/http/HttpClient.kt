package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

/**
 *
 * @author zhangchao
 * time：2020/6/20 22:28
 * description：
 */
class  HttpClient : OkHttpClient() {

    companion object {
        @JvmField
        var INSTANCE: HttpClient = HttpClient()

        val gson = Gson()
    }
    // @NonNull
    //    private static <T> T convert(String json, Type type) {
    //        return gson.fromJson(json, type);
    //    }


    fun <T> convert(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

    fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>) {
        val request = Request.Builder()
                .url("https://api.hencoder.com/$path")
                .build()
        val call = INSTANCE.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("网络异常")
            }

            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                when (code) {
                    in 200..300 -> {
                        val body = response.body()
                        entityCallback.onSuccess(convert(body!!.string(), type) as T)
                    }
                    in 400..499 -> {
                        entityCallback.onFailure("客户端错误")
                    }
                    in 500..600 -> entityCallback.onFailure("服务器错误")
                    else -> entityCallback.onFailure("未知错误")
                }


            }
        })
    }

}