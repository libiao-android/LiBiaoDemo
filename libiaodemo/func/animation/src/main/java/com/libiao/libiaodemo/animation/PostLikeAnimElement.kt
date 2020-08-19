package com.libiao.libiaodemo.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.airbnb.lottie.LottieAnimationView
import java.util.ArrayList
import java.util.LinkedList

class PostLikeAnimElement(var context: Context, var container: ViewGroup, var provide: AnimParamsProvide): IAnimElement {

    private val mViewList = LinkedList<LottieAnimationView>()

    private val mHeightList = ArrayList<AnimInfo>()
    private val mWidthList = ArrayList<AnimInfo>()
    private var mUp = true

    private var mAnimNum = 0

    var directRandom = 0
    var mLastType = -1

    override fun onAnim(startX: Int, startY: Int, type: Int) {

        if(mLastType != type) {
            mHeightList.clear()
            mWidthList.clear()
        }
        mLastType = type
        if(mHeightList.size == 0 || type == 2) {
            if(type == 2) {
                directRandom = 7
            } else {
                directRandom = 0
            }
            mHeightList.clear()
            mWidthList.clear()
            val height = container.height
            val width = container.width

            if(startY < height * 0.2) {
                mUp = false
            }

            var avgeY = startY / 3
            if(startY > 950) {
                avgeY = 950 / 3
            }
            val dX = width - startX

            Log.i("libiao", "startX: $startX")
            val avgeX = width / 10
            Log.i("libiao", "avgeX: $avgeX")
            val numL = startX / avgeX
            if(numL <= 1) directRandom = 6
            val numR = dX / avgeX
            if(numR <= 1) directRandom = 6
            Log.i("libiao", "numL: $numL, numR: $numR")
            val initSinY = 50
            var dY = height - startY
            if(dY > 950) dY = 950
            for(i in 1 .. numL) {
                if(i == 1) {
                    //var tempSin = 45 * startX / (width)
                    var tempSin = 25
                    mHeightList.add(AnimInfo(-startX, -avgeY * 3, tempSin, -1))

                    mWidthList.add(AnimInfo(-startX/3, (dY * 1.5).toInt(), 40, 1))
                } else if(i == 2) {
                    mHeightList.add(AnimInfo((-startX * 1.3).toInt(), -avgeY * 2, 40, -1))

                    mWidthList.add(AnimInfo(-startX * 2 / 3, (dY * 1.3).toInt(), 30, 1))
                } else if(i == 3) {
                    var tempX = (-startX * 1.8).toInt()
                    var tempSin = 60 * width / -tempX
                    mHeightList.add(AnimInfo(tempX, -avgeY * 1, tempSin, -1))

                    mWidthList.add(AnimInfo(-startX*1.8.toInt(), dY, 20, 1))
                }
            }

            for(i in 1 .. numR) {
                if(i == 1) {
                   // var tempSin = 45 * dX / (width)
                    var tempSin = 25
                    mHeightList.add(AnimInfo(dX, -avgeY * 3, tempSin, -1))

                    mWidthList.add(AnimInfo((width - startX)/3, (dY * 1.5).toInt(), 40,  1))
                } else if(i == 2) {
                    mHeightList.add(AnimInfo((dX * 1.3).toInt(), -avgeY * 2, 40, -1))

                    mWidthList.add(AnimInfo((width - startX)*2/3, (dY * 1.3).toInt(), 30,  1))
                } else if(i == 3) {
                    var tempX = (dX * 1.8).toInt()
                    var tempSin = 60 * width / tempX

                    mHeightList.add(AnimInfo(tempX, -avgeY * 1, tempSin, -1))

                    mWidthList.add(AnimInfo((width - startX)*1.8.toInt(), dY, 20,  1))
                }
            }
        }
        var remove1 = -1
        var remove2 = -1
        var remove3 = -1

        if(type == 0) {
            if(mHeightList.size == 4) {
                remove1 = (0 until mHeightList.size).random()
            }
            if(mHeightList.size == 5) {
                remove1 = (0 until mHeightList.size).random()
                remove2 = (remove1 + 3) % mHeightList.size
            } else if(mHeightList.size == 6) {
                remove1 = (0 until mHeightList.size).random()
                remove2 = (remove1 + 3) % mHeightList.size
                remove3 = (remove2 + 3) % mHeightList.size
            }
        }
        for(i in 0 until mHeightList.size) {
            var view = mViewList.poll()
            if(view == null) {
                if(type == 2) {
                    if(mAnimNum >= 30) continue
                    mAnimNum++
                }
                view = LottieAnimationView(context)
                val lottieRes = provide.getLottieRes()
                view.imageAssetsFolder = lottieRes[0]
                view.setAnimation(lottieRes[1])
                view.layoutParams = ViewGroup.LayoutParams(130, 130)
                view.addAnimatorListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        container.removeView(view)
                        view.setMinFrame(0)
                        mViewList.push(view)
                    }
                })
            } else {
                Log.i("libiao", "使用回收的view: ${mViewList.size}")
            }

            if(remove1 == i) continue
            if(remove2 == i) continue
            if(remove3 == i) continue

            view.also {
                it.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                    override fun onPreDraw(): Boolean {

                        if(mHeightList.size == 0) return true

                        var randomY = (-7 .. directRandom).random()
                        var randomInfo: AnimInfo
                        if(randomY < 0) {
                            if(mUp) {
                                randomInfo = mHeightList[i]
                            } else {
                                randomInfo = mWidthList[i]
                            }
                        } else {
                            if(mUp) {
                                randomInfo = mWidthList[i]
                            } else {
                                randomInfo = mHeightList[i]
                            }
                        }

                        val beishu  = (7 .. 13).random() / 10F
                        var x = 0
                        var y = 0
                        if(randomInfo.d < 0) {
                            x = (randomInfo.x * beishu).toInt()
                        } else {
                            y = (randomInfo.y * beishu).toInt()
                        }

                        val time = 1200L
                        val sin = (randomInfo.s - 5 .. randomInfo.s + 5).random() / 100F

                        val animator = ValueAnimator.ofFloat(0F, sin)

                        animator.addUpdateListener { anim ->
                            val value = anim.animatedValue as Float
                            var ratio: Float

                            ratio = Math.sin(value * Math.PI).toFloat()

                            if(randomInfo.d > 0) {
                                it.x = startX + ratio * randomInfo.x
                                it.y = startY + value * y
                            } else {
                                it.x = startX + value * x
                                it.y = startY + ratio * randomInfo.y
                            }

                            it.invalidate()
                        }
                        animator.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {


                            }
                        })
                        animator.interpolator = AccelerateDecelerateInterpolator()
                        animator.duration = time
                        animator.start()

                        val totalFrame = 180
                        var startFrame = 180 - time / (3000 / totalFrame)
                        //Log.i("libiao", "startFrame: $startFrame")
                        startFrame -= 30
                        if(startFrame < 0) startFrame = 0L
                        it.setMinFrame(startFrame.toInt())

                        it.playAnimation()

                        it.viewTreeObserver?.removeOnPreDrawListener(this)
                        return true
                    }

                })
            }
            container.addView(view)
        }
    }

    override fun onDestroy() {
        mHeightList.clear()
        mWidthList.clear()
        mUp = true
        mViewList.clear()
        mAnimNum = 0
        directRandom = 0
    }

    class AnimInfo(dX: Int, dY: Int, sin: Int, direct: Int) {
        var x = dX
        var y = dY
        var s = sin
        var d = direct
    }
}