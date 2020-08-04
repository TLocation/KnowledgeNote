package com.example.designpatterns.builder

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:06

 */
class MoonComputerBuilder : Builder() {
    private val computer = ComputerOne()
    override fun buildeCpu(cpu: String?) {
        computer.cpu = cpu
    }

    override fun bulidMainboard(mainboard: String?) {
        computer.mainboard = mainboard
    }

    override fun buildRam(ram: String?) {
        computer.ram = ram
    }

    override fun create(): ComputerOne? {
        return computer
    }
}