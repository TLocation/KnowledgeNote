package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 15:04

 */
fun main() {
    val computerfactory: ComputerFactory = ComputerFactory()
    val createrComputer = computerfactory.createrComputer("lenovo")
    createrComputer.start()
}

class FactoryTest {
}