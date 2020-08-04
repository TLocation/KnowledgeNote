package com.example.designpatterns.observer

/**

 * 作者：李艳东

 * 日期：on 2020/7/19 14:18

 */
fun main() {
    val subscriptionSubject = SubscriptionSubject()
    val user1 = WeixinUser("jake")
    val user2 = WeixinUser("tom")
    val user3 = WeixinUser("herry")
    subscriptionSubject.attach(user1);
    subscriptionSubject.attach(user2);
    subscriptionSubject.attach(user3);
    subscriptionSubject.notify("hahahahahaa");
}

class ObserveDo {
}