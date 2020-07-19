package com.example.designpatterns.builder

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:10

 */
class Director {
    var builder: Builder? = null

    constructor(builder: Builder?) {
        this.builder = builder
    }

    fun CreateCompter(
        cpu: String?,
        mainboard: String?,
        ram: String?
    ): ComputerOne? {
        builder!!.buildeCpu(cpu)
        builder!!.buildRam(ram)
        builder!!.bulidMainboard(mainboard)
        return builder!!.create()
    }
}