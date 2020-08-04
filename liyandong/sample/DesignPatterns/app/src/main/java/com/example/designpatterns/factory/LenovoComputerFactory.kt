package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 15:14

 */
class LenovoComputerFactory : Provider {
    override fun produce(): Computer {
        return LenovoComputer()
    }
}