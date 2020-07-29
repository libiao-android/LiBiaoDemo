package com.libiao.libiaodemo.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import com.airbnb.lottie.LottieAnimationView
import java.util.LinkedList

class PostLikeAnimElement(var context: Context, var container: ViewGroup, var provide: AnimParamsProvide): IAnimElement {

    private val mViewList = LinkedList<LottieAnimationView>()

    override fun onAnim(startX: Int, startY: Int) {
        var view = mViewList.poll()
        if(view == null) {
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
            //Log.i("libiao", "使用回收的view")
        }
        //Log.i("libiao", "PostLikeAnimElement")
        view.also {
            it.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {

                    val containerW = container.width
                    val containerH = container.height

                    val distanceX = (containerW - startX) / 50

                    var randomX = (-startX / 50 .. distanceX).random() * 50
                    if(randomX > 0) {
                        randomX += 200
                    } else {
                        randomX -= 200
                    }
                   // Log.i("libiao", "randomX = $randomX")

                    val distanceY = (containerH - startY) / 50

                    var randomY = (-startY / 50 .. distanceY).random() * 50

                    if(randomY < 0) {
                        if(randomY > -200) {
                            randomY -= 200
                        } else if(randomY < -700) {
                            randomY += 200
                        }
                    }

                    //Log.i("libiao", "randomY = $randomY")

                    val time = (10L .. 20).random() * 100
                    val sin = (1 .. 9).random() / 10F + 1

                    val animator = ValueAnimator.ofFloat(0F, 1F)

                    animator.addUpdateListener { anim ->
                        val value = anim.animatedValue as Float
                        var ratio: Float
                        if(randomY > 0) {
                            ratio = Math.sin(value * Math.PI / 2).toFloat()
                            it.x = startX + ratio * randomX
                            it.y = startY + value * randomY

                        } else {
                            ratio = Math.sin(value * Math.PI / sin).toFloat()
                            it.x = startX + value * randomX
                            it.y = startY + ratio * randomY
                        }
                        //Log.i("libiao","value: $value")
                        //Log.i("libiao", "it.x: ${it.x}")
                        //Log.i("libiao", "it.y: ${it.y}")

                        it.invalidate()
                    }
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {


                        }
                    })
                    animator.interpolator = LinearInterpolator()
                    animator.duration = time
                    animator.start()

//                    Log.i("libiao", "view.duration: ${it.duration}")
//                    Log.i("libiao", "view.speed: ${it.speed}")
//                    Log.i("libiao", "view.frame: ${it.frame}")
//                    Log.i("libiao", "view.minFrame: ${it.minFrame}")
//                    Log.i("libiao", "view.maxFrame: ${it.maxFrame}")
//                    Log.i("libiao", "view.progress: ${it.progress}")m
                    val totalFrame = 180
                    var startFrame = 180 - time / (3000 / totalFrame)
                    //Log.i("libiao", "startFrame: $startFrame")
                    //startFrame -= 30
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

    override fun onDestroy() {

    }
}