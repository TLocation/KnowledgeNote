package com.example.designpatterns.adapter.objectAdapter

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:57

 */
open class Adapter {
    //适配者接口
    fun specificRequest() {
        println("适配者中的业务代码被调用！")
    }
}