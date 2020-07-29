package com.libiao.libiaodemo.animation

import android.content.Context
import android.view.ViewGroup

class AnimElementPool(var context: Context) {

    companion object {
        const val RECEIVE_LIKE_ANIM = 1
        const val POST_LIKE_ANIM = 2
        const val POST_LIKE_TEXT = 3
    }

    fun obtain(type: Int, provide: AnimParamsProvide, container: ViewGroup) : IAnimElement? {
        return when(type) {
            RECEIVE_LIKE_ANIM -> {
                ReceiveLikeAnimElement(context, container, provide)
            }
            POST_LIKE_ANIM -> {
                PostLikeAnimElement(context, container, provide)
            }
            POST_LIKE_TEXT -> {
                PostLikeTextElement(context, container, provide)
            }
            else -> null
        }
    }
}