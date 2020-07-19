package com.example.designpatterns.singleton

/**

 * 作者：李艳东

 * 日期：on 2020/7/18 14:22

 */
class LazyManPatternSecurity {
    private var sington: LazyManPatternSecurity? = null
    private fun Sington() {}

    @Synchronized
    fun getInstance(): LazyManPatternSecurity? {
        if (sington == null) {
            sington = LazyManPatternSecurity()
        }
        return sington
    }
}