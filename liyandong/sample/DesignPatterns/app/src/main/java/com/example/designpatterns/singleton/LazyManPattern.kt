package com.example.designpatterns.singleton

/**

 * 作者：LENOVO

 * 日期：on 2020/7/18 14:18

 */
class LazyManPattern {
    //懒汉模式（线程不安全）
    private var sington: LazyManPattern? = LazyManPattern()
    fun getInstance(): LazyManPattern? {
        if (sington == null) {
            sington = LazyManPattern()
        }
        return sington
    }
}