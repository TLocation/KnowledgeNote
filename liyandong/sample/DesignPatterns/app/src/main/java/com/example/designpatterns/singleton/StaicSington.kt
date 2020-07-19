package com.example.designpatterns.singleton

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 14:42

 */
class StaicSington {
    //静态内部类单例模式
    constructor()

    fun getInstance(): StaicSington? {
        return SingletonHolder.singleton
    }

    private object SingletonHolder {
        internal val singleton = StaicSington()
    }


}

//枚举单例，线程安全
internal enum class Singleton {
    INSTANCE;

    fun doSomeThing() {}
}