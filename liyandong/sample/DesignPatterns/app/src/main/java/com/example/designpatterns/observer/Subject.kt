package com.example.designpatterns.observer

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:10

 */
interface Subject {
    fun attach(observer: Observer?)
    fun detach(observer: Observer?)
    fun notify(message: String?)
}