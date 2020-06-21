package com.example.core.http

/**
 *
 * @author zhangchao
 * time：2020/6/21 14:12
 * description：
 */
interface EntityCallback<T> {

    fun onSuccess(t :T)

    fun onFailure(message : String?)

}