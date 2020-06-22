package com.example.core.http

/**
 *
 * @author tianxiaolong
 * time：2020/6/20 9:01 PM
 * description：
 */
interface EntityCallback<T> {
    fun onSuccess(entity: T)

    fun onFailure(message: String?)
}