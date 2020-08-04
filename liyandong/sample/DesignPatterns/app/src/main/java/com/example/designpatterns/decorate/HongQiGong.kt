package com.example.designpatterns.decorate

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:51

 */
class HongQiGong(swordsman: Swordsman) : Master(swordsman) {
    fun teachAttackMagic() {
        println("洪七公打狗棒")
        println("杨过使用打狗棒")
    }
}