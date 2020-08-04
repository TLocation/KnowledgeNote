package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 15:19

 */
fun main() {
    val provider: Provider = HpComputerFactory()
    val sender: Computer = provider.produce()
    sender.start()
}

class Test {
}