package com.example.designpatterns.adapter.classadapter

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:58

 */
class ClassAdapter : Adapter(), Target {
    override fun request() {
        specificRequest()
    }
}