package com.example.designpatterns.builder

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:14

 */
fun main() {
    val director = Director(MoonComputerBuilder())
    val createCompter = director.CreateCompter("5564645", "54aas", "sd")

    if (createCompter != null) {
        println(createCompter.cpu + createCompter.mainboard + createCompter.ram)
    }


}

class Test {

}