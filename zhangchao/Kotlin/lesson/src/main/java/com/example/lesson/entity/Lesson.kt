package com.example.lesson.entity

/**
 *
 * @author zhangchao
 * time：2020/6/21 14:03
 * description：
 */
class Lesson {

    enum class State {
        PLAYBACK {
             fun stateName(): String {
                return "有回放"
            }
        },

        LIVE {
             fun stateName(): String {
                return "正在直播"
            }
        },

        WAIT {
             fun stateName(): String {
                return "等待中"
            }
        };

         var stateName : String? = null
    }
     var date: String? = null
     var content: String? = null
     var state: State? = null

    fun Lesson(date: String, content: String, state: State) {
        this.date = date
        this.content = content
        this.state = state
    }
}