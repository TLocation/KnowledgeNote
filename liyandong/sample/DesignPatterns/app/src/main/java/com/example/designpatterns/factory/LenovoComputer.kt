package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 14:58

 */
class LenovoComputer :Computer() {
    override fun start() {
        println("联想电脑")
    }
}