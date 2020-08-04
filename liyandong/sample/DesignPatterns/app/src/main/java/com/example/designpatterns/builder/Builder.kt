package com.example.designpatterns.builder

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:04

 */
abstract class Builder {
    abstract fun buildeCpu(cpu: String?)
    abstract fun bulidMainboard(mainboard: String?)
    abstract fun buildRam(ram: String?)
    abstract fun create(): ComputerOne?
}