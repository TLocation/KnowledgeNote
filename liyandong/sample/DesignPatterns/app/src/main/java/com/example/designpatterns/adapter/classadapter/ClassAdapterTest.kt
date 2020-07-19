package com.example.designpatterns.adapter.classadapter

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:59

 */
fun main() {
    println("类适配器测试：")
    val target: Target =
        ClassAdapter()
    target.request()
}

class ClassAdapterTest {
}