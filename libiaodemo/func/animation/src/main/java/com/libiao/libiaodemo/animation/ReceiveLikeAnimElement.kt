package com.libiao.libiaodemo.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import java.util.LinkedList

class ReceiveLikeAnimElement(var context: Context, var container: ViewGroup, var provide: AnimParamsProvide): IAnimElement {

    private val mViewList = LinkedList<ImageView>()

    override fun onAnim(startX: Int, startY: Int, type: Int) {
        var view = mViewList.poll()
        if(view == null) {
            view = ImageView(context)
            view.layoutParams = ViewGroup.LayoutParams(130, 130)
        } else {
            Log.i("libiao", "使用回收的view")
        }
        view.also {
            it.setImageBitmap(provide.getBitmap())
            it.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {

                    val containerWidth = container.width
                    val containerHeight = container.height

                    val animator = ValueAnimator.ofFloat(0F, 1F)
                    val m = (-4 ..  4).random() / 5F

                    val halfContainerWidth = containerWidth / 2

                    animator.addUpdateListener { anim ->
                        val value = anim.animatedValue as Float
                        if(value < 0.5) {
                            it.x = startX + value * halfContainerWidth * m * 2
                            if(it.x < 0) it.x = 0F
                        }
                        if(value < 0.2) {
                            it.scaleX = value * 5
                            if(it.scaleX > 1) it.scaleX = 1F
                            it.scaleY = value * 5
                            if(it.scaleY > 1) it.scaleY = 1F
                        }
                        it.y = startY - containerHeight * value
                        it.alpha = 1 - value
                        it.invalidate()
                    }
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            container.removeView(it)
                            mViewList.push(it)
                        }
                    })
                    animator.duration = provide.getDuration()
                    animator.start()

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