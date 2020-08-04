package com.example.designpatterns.decorate

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:52

 */

fun main() {
    val yangGuo = YangGuo()
    val hongQiGong = HongQiGong(yangGuo)
    hongQiGong.attackMagic()
}

class Decoration {
}