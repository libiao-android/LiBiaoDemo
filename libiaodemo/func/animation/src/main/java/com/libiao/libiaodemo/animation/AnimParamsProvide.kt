package com.libiao.libiaodemo.animation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class AnimParamsProvide(var context: Context) {

    private val mBitmapList = ArrayList<Bitmap>()
    private var mAnimDuration = 2000L
    private var mLastTypeIndex = -1

    private var mElementWidth = 0
    private var mElementHeight = 0

    private val mLottieFolder = arrayOf("lottie/like1/images/", "lottie/like2/images/", "lottie/like3/images/", "lottie/like4/images/", "lottie/like5/images/")
    private val mLottieAnimor = arrayOf("lottie/like1/data.json", "lottie/like2/data.json", "lottie/like3/data.json", "lottie/like4/data.json", "lottie/like5/data.json")

    fun getBitmap(): Bitmap {
        if(mBitmapList.isEmpty()) {
            mBitmapList.add(BitmapFactory.decodeResource(context.resources, R.mipmap.like1))
            mBitmapList.add(BitmapFactory.decodeResource(context.resources, R.mipmap.like2))
            mBitmapList.add(BitmapFactory.decodeResource(context.resources, R.mipmap.like3))
            mBitmapList.add(BitmapFactory.decodeResource(context.resources, R.mipmap.like4))
            mBitmapList.add(BitmapFactory.decodeResource(context.resources, R.mipmap.like5))
        }
        var random = (0 until mBitmapList.size).random()
        while(mLastTypeIndex == random) {
            random = (0 until mBitmapList.size).random()
        }
        mLastTypeIndex = random
        return mBitmapList[random]
    }

    fun setBitmap(resId: Int) {
        mBitmapList.add(BitmapFactory.decodeResource(context.resources, resId))
    }

    fun getDuration(): Long {
        return mAnimDuration
    }

    fun setDuration(duration: Long) {
        if(duration > 0) {
            mAnimDuration = duration
        }
    }

    fun getLottieRes():Array<String> {
        var random = (0 until mLottieFolder.size).random()
        while(mLastTypeIndex == random) {
            random = (0 until mLottieFolder.size).random()
        }
        mLastTypeIndex = random
        return arrayOf(mLottieFolder[random], mLottieAnimor[random])
    }

    fun getTextNumRes(count: Int): Int {
        return when(count) {
            0 -> R.mipmap.text0
            1 -> R.mipmap.text1
            2 -> R.mipmap.text2
            3 -> R.mipmap.text3
            4 -> R.mipmap.text4
            5 -> R.mipmap.text5
            6 -> R.mipmap.text6
            7 -> R.mipmap.text7
            8 -> R.mipmap.text8
            9 -> R.mipmap.text9
            else -> 0
        }
    }

    fun getTextRes(count: Int): Int {
        return R.mipmap.text
    }

    fun setElementWidth(width: Int) {
        mElementWidth = width
    }
    fun getElementWidth(): Int {
        return mElementWidth
    }

    fun setElementHeight(height: Int) {
        mElementHeight = height
    }
    fun getElementHeight(): Int {
        return mElementHeight
    }
}