package com.libiao.libiaodemo.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class PostLikeTextElement(var context: Context, var container: ViewGroup, var provide: AnimParamsProvide) : IAnimElement {

    private var mTextParent: View? = null
    private var iv1: ImageView? = null
    private var iv2: ImageView? = null
    private var iv3: ImageView? = null
    private var iv4: ImageView? = null
    private var iv5: ImageView? = null
    private var mCount = 0

    private var mScaleAnimSet: AnimatorSet? = null

    init {
        mTextParent = LayoutInflater.from(context).inflate(R.layout.like_text_view, container, false)
        iv1 = mTextParent?.findViewById(R.id.iv_like_text1)
        iv2 = mTextParent?.findViewById(R.id.iv_like_text2)
        iv3 = mTextParent?.findViewById(R.id.iv_like_text3)
        iv4 = mTextParent?.findViewById(R.id.iv_like_text4)
        iv5 = mTextParent?.findViewById(R.id.iv_like_text5)
        mTextParent?.visibility = View.GONE
        container.addView(mTextParent)
    }

    override fun onAnim(startX: Int, startY: Int, type: Int) {
        var x = startX
        var y = startY
        if(x < 0) x = 0
        if(y < 0) y = 0
        if(container.width - x < mTextParent?.width ?: 0) {
            x = container.width - (mTextParent?.width ?: 0)
        }
        Log.i("libiao", "${mTextParent?.width}")
        Log.i("libiao", "${mTextParent?.height}")
        mTextParent?.x = x.toFloat()
        mTextParent?.y = y.toFloat()
        if(mCount < 10) {
            iv1?.visibility = View.VISIBLE
            iv1?.setImageResource(provide.getTextNumRes(mCount))
        } else if(mCount < 100) {
            iv2?.visibility = View.VISIBLE
            iv1?.setImageResource(provide.getTextNumRes(mCount / 10))
            iv2?.setImageResource(provide.getTextNumRes(mCount % 10))
        } else if(mCount < 1000) {
            iv3?.visibility = View.VISIBLE
            iv1?.setImageResource(provide.getTextNumRes(mCount / 100))
            iv2?.setImageResource(provide.getTextNumRes(mCount / 10 % 10))
            iv3?.setImageResource(provide.getTextNumRes(mCount % 10))
        } else {
            if(mCount > 9999) mCount = 9999
            iv4?.visibility = View.VISIBLE
            iv1?.setImageResource(provide.getTextNumRes(mCount / 1000))
            iv2?.setImageResource(provide.getTextNumRes(mCount / 100 % 10))
            iv3?.setImageResource(provide.getTextNumRes(mCount / 10 % 10))
            iv4?.setImageResource(provide.getTextNumRes(mCount % 10))
        }
        mCount++
        mTextParent?.visibility = View.VISIBLE
        mTextParent?.invalidate()

        iv5?.also {
            if(mScaleAnimSet == null) {
                it.setImageResource(provide.getTextRes(mCount))
                mScaleAnimSet = AnimatorSet()
                val textView = it as View
                val scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 1F, 1.3F, 1F)
                scaleX.repeatCount = ValueAnimator.INFINITE
                val scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 1F, 1.3F, 1F)
                scaleY.repeatCount = ValueAnimator.INFINITE
                mScaleAnimSet?.duration = 240
                mScaleAnimSet?.play(scaleX)?.with(scaleY)
                mScaleAnimSet?.start()
            }
        }

    }

    override fun onDestroy() {
        mCount = 0
        iv1?.visibility = View.GONE
        iv2?.visibility = View.GONE
        iv3?.visibility = View.GONE
        iv4?.visibility = View.GONE
        mTextParent?.visibility = View.GONE
        mScaleAnimSet?.cancel()
        mScaleAnimSet = null
    }

}