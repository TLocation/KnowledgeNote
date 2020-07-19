package com.example.designpatterns.decorate

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 16:47

 */
abstract class Master : Swordsman {
    private var swordsman: Swordsman? = null

    constructor(swordsman: Swordsman?) {
        this.swordsman = swordsman
    }

    override fun attackMagic() {
        swordsman?.attackMagic();
    }
}