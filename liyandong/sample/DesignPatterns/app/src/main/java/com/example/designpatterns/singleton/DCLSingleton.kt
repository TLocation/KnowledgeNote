package com.example.designpatterns.singleton

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 14:28

 */
class DCLSingleton {
    //双重判断模式
    private var dclSingleton: DCLSingleton? = null

    constructor()

    fun getInstance(): DCLSingleton? {
        if (dclSingleton == null) {
            synchronized(DCLSingleton::class.java) {
                if (dclSingleton == null) {
                    dclSingleton = DCLSingleton()
                }
            }
        }
        return dclSingleton
    }
}