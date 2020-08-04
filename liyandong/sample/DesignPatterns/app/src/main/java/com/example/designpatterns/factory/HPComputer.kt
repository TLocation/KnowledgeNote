package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 14:59

 */
class HPComputer:Computer() {
    override fun start() {
        println("惠普电脑")
    }
}