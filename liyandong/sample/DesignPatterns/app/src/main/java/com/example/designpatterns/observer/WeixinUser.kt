package com.example.designpatterns.observer

/**

 * 作者：李狗东

 * 日期：on 2020/7/18 22:29

 */
class WeixinUser : Observer {
    private var name: String? = null

    constructor(name: String) {
        this.name = name
    }

    override fun update(message: String?) {
        println("name+\"-\"+message")
    }
}