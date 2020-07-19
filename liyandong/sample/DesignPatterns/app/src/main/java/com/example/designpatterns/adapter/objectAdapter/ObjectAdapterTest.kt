package com.example.designpatterns.adapter.objectAdapter

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 15:20

 */
fun main() {
    println("对象适配器模式测试：")
    val adaptee = Adapter()
    val target: Target =
        ObjectAdapter(adaptee)
    target.request()
}

class ObjectAdapterTest {
}