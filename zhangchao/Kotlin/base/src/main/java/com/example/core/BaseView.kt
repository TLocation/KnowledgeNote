package com.example.core

/**
 *
 * @author zhangchao
 * time：2020/6/20 23:37
 * description：
 */
interface BaseView<T> {

     fun getPresenter(): T
}