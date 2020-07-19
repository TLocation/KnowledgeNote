package com.example.designpatterns.factory

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 15:00

 */
class ComputerFactory {
    fun createrComputer(type: String): Computer {
        var computer: Computer? = null
        when (type) {
            "lenovo" -> computer = LenovoComputer()
            "hp" -> computer = HPComputer()
            else -> {
            }
        }
        return computer!!
    }
}