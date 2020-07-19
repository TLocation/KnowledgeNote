package com.example.designpatterns.observer

import android.R.id


/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:11

 */
class SubscriptionSubject : Subject {
    private val observeDos: ArrayList<Observer?> =
        ArrayList()


    override fun attach(observer: Observer?) {
        observeDos.add(observer)
    }

    override fun detach(observer: Observer?) {
        observeDos.remove(observer)
    }

    override fun notify(message: String?) {
        for (observer in observeDos) {
            observer?.update(message)
        }
    }
}